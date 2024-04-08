package cloud.deuterium.maxbet.combat.functions;

import cloud.deuterium.maxbet.combat.events.CombatCommandEvent;
import cloud.deuterium.maxbet.combat.events.CombatStartEvent;
import cloud.deuterium.maxbet.combat.events.CombatUpdateEvent;
import cloud.deuterium.maxbet.combat.service.BroadcasterService;
import cloud.deuterium.maxbet.combat.service.UpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

import static cloud.deuterium.maxbet.combat.enums.CombatStatus.ACTIVE;

/**
 * Created by Milan Stojkovic 06-Apr-2024
 */

@Slf4j
@Configuration
public class CombatConsumers {

    @Bean
    public Consumer<Flux<CombatStartEvent>> onCombatStartEvent(BroadcasterService broadcaster) {
        return c -> c
                .doOnNext(event -> log.debug("Consuming Combat Start Event {}", event.combatId()))
                .map(broadcaster::addTerminator)
                .doOnError(e -> log.error("Combat Start Error: {}", e.getMessage()))
                .subscribe();
    }

    @Bean
    public Consumer<Flux<CombatUpdateEvent>> onCombatUpdateEvent(UpdateService service) {
        return c -> c
                .doOnNext(event -> log.debug("Consuming Combat Update Event {}", event))
                .flatMap(service::getChallenge)
                .filter(t2 -> ACTIVE.equals(t2.getT1().getStatus()))
                .map(service::calculate)
                .flatMap(service::update)
                .doOnError(e -> log.error("Combat Update Error: {}", e.getMessage()))
                .subscribe();
    }

    @Bean
    public Consumer<Flux<CombatCommandEvent>> onCombatCommandEvent(BroadcasterService broadcaster) {
        return  c -> c
                .doOnNext(event -> log.debug("Consuming Combat Command Event {}", event.getCombatId()))
                .map(broadcaster::process)
                .doOnError(e -> log.error("Combat Command Error: {}", e.getMessage()))
                .subscribe();
    }
}
