package cloud.deuterium.maxbet.combat.service;

import cloud.deuterium.maxbet.combat.dtos.ChallengeRequest;
import cloud.deuterium.maxbet.combat.dtos.ChallengeResponse;
import cloud.deuterium.maxbet.combat.enums.Action;
import cloud.deuterium.maxbet.combat.entities.Combat;
import cloud.deuterium.maxbet.combat.enums.CombatStatus;
import cloud.deuterium.maxbet.combat.events.CombatCommandEvent;
import cloud.deuterium.maxbet.combat.events.CombatStartEvent;
import cloud.deuterium.maxbet.combat.exceptions.BadRequestException;
import cloud.deuterium.maxbet.combat.exceptions.ExternalServerException;
import cloud.deuterium.maxbet.combat.exceptions.ForbiddenException;
import cloud.deuterium.maxbet.combat.exceptions.NotAuthorizedException;
import cloud.deuterium.maxbet.combat.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.UUID;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CombatService {

    private final ChallengeRepository repository;
    private final Sinks.Many<CombatCommandEvent> combatCommandSink;
    private final Sinks.Many<CombatStartEvent> combatStartSink;
    private final WebClient webClient;

    public Mono<UUID> challenge(ChallengeRequest cRequest, Jwt jwt) {
        log.debug("Challenge request: {}", cRequest);
        return isCharactersAvailable(cRequest.getChallengerId(), cRequest.getChallengedId())
                .handle(((aBoolean, synchronousSink) -> {
                    if (aBoolean) {
                        synchronousSink.next(true);
                    } else {
                        log.info("Some player are not available for duel");
                        synchronousSink.error(new BadRequestException("Some player is not available for a duel"));
                    }
                }))
                .flatMap(b -> callCharacterService(cRequest, jwt.getTokenValue()))
                .map(this::mapToCombat)
                .flatMap(repository::save)
                .doOnNext(e -> emitStartEvent(e.getId()))
                .mapNotNull(Combat::getId);
    }

    public Mono<Boolean> emitCommandEvent(UUID combatId, String userId, Action action) {
        return repository.isResourceOwner(CombatStatus.ACTIVE, combatId, userId)
                .handle((b, sink) -> {
                    if (b) {
                        log.info("Emit Command event: {}", combatId);
                        CombatCommandEvent event = CombatCommandEvent.builder()
                                .combatId(combatId)
                                .userId(userId)
                                .action(action)
                                .build();
                        combatCommandSink.tryEmitNext(event);
                        sink.next(true);
                    } else {
                        sink.error(new ForbiddenException("You don't have permission for Combat with ID " + combatId));
                    }
                });
    }

    private @NotNull Mono<ChallengeResponse> callCharacterService(ChallengeRequest cRequest, String jwt) {
        return webClient.post()
                .uri("/api/character/challenge")
                .header("Authorization", "Bearer " + jwt)
                .body(Mono.just(cRequest), ChallengeRequest.class)
                .retrieve()
                .onStatus(status -> status.value() == 401, res -> Mono.error(new NotAuthorizedException("Not Authorized")))
                .onStatus(HttpStatusCode::is4xxClientError, res -> Mono.error(new BadRequestException("Bad request")))
                .onStatus(HttpStatusCode::is5xxServerError, res -> Mono.error(new ExternalServerException("External Server Error")))
                .bodyToMono(ChallengeResponse.class)
                .doOnError(e -> log.error("Character Service call Error: {}", e.getMessage()));
    }

    private Combat mapToCombat(ChallengeResponse res) {
        return Combat.builder()
                .challengerId(res.getChallengerId())
                .challengerUserId(res.getChallengerUserId())
                .challengerHealth(res.getChallengerHealth())
                .challengerMana(res.getChallengerMana())
                .challengerAttack(res.getChallengerAttack())
                .challengerCast(res.getChallengerCast())
                .challengerHeal(res.getChallengerHeal())
                .challengedId(res.getChallengedId())
                .challengedUserId(res.getChallengedUserId())
                .challengedHealth(res.getChallengedHealth())
                .challengedMana(res.getChallengedMana())
                .challengedAttack(res.getChallengedAttack())
                .challengedCast(res.getChallengedCast())
                .challengedHeal(res.getChallengedHeal())
                .status(CombatStatus.ACTIVE)
                .build();
    }

    private void emitStartEvent(UUID combatId) {
        log.info("Emit Start event: {}", combatId);
        combatStartSink.tryEmitNext(new CombatStartEvent(combatId));
    }

    private Mono<Boolean> isCharactersAvailable(String challengerId, String challengedId) {
        return repository.isCharactersAvailable(CombatStatus.ACTIVE, challengerId, challengedId);
    }
}
