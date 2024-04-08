package cloud.deuterium.maxbet.combat.controllers;

import cloud.deuterium.maxbet.combat.dtos.ChallengeRequest;
import cloud.deuterium.maxbet.combat.exceptions.NotAuthorizedException;
import cloud.deuterium.maxbet.combat.service.CombatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static cloud.deuterium.maxbet.combat.enums.Action.*;
import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/challenges")
public class CombatController {

    private final CombatService service;

    @PostMapping
    public Mono<UUID> challenge(@RequestBody ChallengeRequest cRequest,
                                @AuthenticationPrincipal Jwt jwt) {
        log.info("Create Challenge for Characters: {} and {}", cRequest.getChallengerId(), cRequest.getChallengedId());
        return service.challenge(cRequest, jwt);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{combatId}/attack")
    public Mono<Void> attack(@PathVariable UUID combatId, @AuthenticationPrincipal Jwt jwt) {
        String userId = extractUserId(jwt);
        log.info("Combat: {} -> Character with id: {} will Attack", combatId, userId);
        return service.emitCommandEvent(combatId, userId, ATTACK)
                .then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{combatId}/cast")
    public Mono<Void> cast(@PathVariable UUID combatId, @AuthenticationPrincipal Jwt jwt) {
        String userId = extractUserId(jwt);
        log.info("Combat: {} -> Character with id: {} will Cast", combatId, userId);
        return service.emitCommandEvent(combatId, userId, CAST)
                .then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{combatId}/heal")
    public Mono<Void> heal(@PathVariable UUID combatId, @AuthenticationPrincipal Jwt jwt) {
        String userId = extractUserId(jwt);
        log.info("Combat: {} -> User with id: {} will Heal", combatId, userId);
        return service.emitCommandEvent(combatId, userId, HEAL)
                .then();
    }

    private String extractUserId(Jwt jwt) {
        Object id = jwt.getClaims().get("id");
        if (isNull(id)) {
            throw new NotAuthorizedException("Not authorized!");
        }
        return id.toString();
    }
}
