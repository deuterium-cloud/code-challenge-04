package cloud.deuterium.maxbet.combat.service;

import cloud.deuterium.maxbet.combat.entities.Combat;
import cloud.deuterium.maxbet.combat.enums.CombatStatus;
import cloud.deuterium.maxbet.combat.events.CombatStopEvent;
import cloud.deuterium.maxbet.combat.events.CombatUpdateEvent;
import cloud.deuterium.maxbet.combat.events.WinnerEvent;
import cloud.deuterium.maxbet.combat.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.function.Tuple2;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */

@RequiredArgsConstructor
@Slf4j
@Service
public class UpdateService {

    private final ChallengeRepository repository;
    private final Sinks.Many<CombatStopEvent> combatStopSink;

    public Mono<Tuple2<Combat, CombatUpdateEvent>> getChallenge(CombatUpdateEvent event) {
        Mono<Combat> combatMono = repository.findById(event.getCombatId());
        return Mono.zip(combatMono, Mono.just(event));
    }

    public Combat calculate(Tuple2<Combat, CombatUpdateEvent> t2) {

        Combat combat = t2.getT1();
        CombatUpdateEvent event = t2.getT2();

        log.debug("Combat Update: {} {}", event.getAction(), event.getCombatId());

        String userId = event.getUserId();

        if (userId.equals(combat.getChallengerUserId())) {
            changeByChallenger(event, combat);
        } else {
            changeByChallenged(event, combat);
        }

        return combat;
    }

    private void changeByChallenger(CombatUpdateEvent event, Combat combat) {
        switch (event.getAction()) {
            case ATTACK -> {
                int health = combat.getChallengedHealth() - combat.getChallengerAttack();
                if (health <= 0) {
                    combat.setChallengedHealth(0);
                    combat.setStatus(CombatStatus.FINISHED);
                    log.debug("Create Stop event from Challenger's ATTACK {}", event.getCombatId());
                    combatStopSink.tryEmitNext(new CombatStopEvent(combat.getId()));
                } else {
                    combat.setChallengedHealth(health);
                }
            }
            case CAST -> {
                int health = combat.getChallengedHealth() - combat.getChallengerCast();
                if (health <= 0) {
                    combat.setChallengedHealth(0);
                    combat.setStatus(CombatStatus.FINISHED);
                    log.debug("Create Stop event from Challenger's CAST {}", event.getCombatId());
                    combatStopSink.tryEmitNext(new CombatStopEvent(combat.getId()));
                } else {
                    combat.setChallengedHealth(health);
                }
            }
            case HEAL -> {
                int health = combat.getChallengerHealth() + combat.getChallengerHeal();
                combat.setChallengerHealth(health);
            }
        }
    }

    private void changeByChallenged(CombatUpdateEvent event, Combat combat) {
        switch (event.getAction()) {
            case ATTACK -> {
                int health = combat.getChallengerHealth() - combat.getChallengedAttack();
                if (health <= 0) {
                    combat.setChallengerHealth(0);
                    combat.setStatus(CombatStatus.FINISHED);
                    log.debug("Create Stop event from Challenged ATTACK {}", event.getCombatId());
                    combatStopSink.tryEmitNext(new CombatStopEvent(combat.getId()));
                } else {
                    combat.setChallengerHealth(health);
                }
            }
            case CAST -> {
                int health = combat.getChallengerHealth() - combat.getChallengedCast();
                if (health <= 0) {
                    combat.setChallengerHealth(0);
                    combat.setStatus(CombatStatus.FINISHED);
                    log.debug("Create Stop event from Challenged CAST {}", event.getCombatId());
                    combatStopSink.tryEmitNext(new CombatStopEvent(combat.getId()));
                } else {
                    combat.setChallengerHealth(health);
                }
            }
            case HEAL -> {
                int health = combat.getChallengedHealth() + combat.getChallengedHeal();
                combat.setChallengedHealth(health);
            }
        }
    }

    public Mono<Void> update(Combat combat) {
        return repository.save(combat)
                .then();
    }

    public Mono<CombatStopEvent> updateStatus(CombatStopEvent event) {
        return repository.updateCombatStatus(event.combatId(), CombatStatus.FINISHED)
                .then(Mono.just(event));
    }

    public Mono<WinnerEvent> createWinnerEvent(CombatStopEvent event) {
        return repository.findById(event.combatId())
                .flatMap(this::calculateWinner);
    }

    private Mono<WinnerEvent> calculateWinner(Combat combat) {
        int crHealth = combat.getChallengerHealth();
        int cdHealth = combat.getChallengedHealth();

        if (crHealth > 0 & cdHealth > 0) {
            log.debug("Result is Winner");
            return Mono.empty(); // Draw
        }

        if (crHealth == 0) {
            // 2nd Player is Winner
            log.debug("2nd Player is Winner {}", combat.getChallengedId());
            return Mono.just(new WinnerEvent(combat.getChallengedId(), combat.getChallengerId()));
        } else {
            // 1st Player is Winner
            log.debug("1st Player is Winner {}", combat.getChallengerId());
            return Mono.just(new WinnerEvent(combat.getChallengerId(), combat.getChallengedId()));
        }
    }
}
//WinnerEvent(winnerId=995658ff-144b-4f19-979f-811aaa063641, loserId=995658ff-144b-4f19-979f-811aaa063641)
