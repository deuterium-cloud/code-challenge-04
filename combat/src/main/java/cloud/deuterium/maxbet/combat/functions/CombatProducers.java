package cloud.deuterium.maxbet.combat.functions;

import cloud.deuterium.maxbet.combat.events.CombatCommandEvent;
import cloud.deuterium.maxbet.combat.events.CombatStartEvent;
import cloud.deuterium.maxbet.combat.events.CombatStopEvent;
import cloud.deuterium.maxbet.combat.events.CombatUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */

@Slf4j
@Configuration
public class CombatProducers {

    @Bean
    public Supplier<Flux<CombatStartEvent>> combatStartSupplier(Flux<CombatStartEvent> flux) {
        return () -> flux;
    }

    @Bean
    public Supplier<Flux<CombatUpdateEvent>> combatUpdateSupplier(Flux<CombatUpdateEvent> flux) {
        return () -> flux;
    }

    @Bean
    public Supplier<Flux<CombatStopEvent>> combatStopSupplier(Flux<CombatStopEvent> flux) {
        return () -> flux;
    }

    @Bean
    public Supplier<Flux<CombatCommandEvent>> combatCommandSupplier(Flux<CombatCommandEvent> flux) {
        return () -> flux;
    }
}
