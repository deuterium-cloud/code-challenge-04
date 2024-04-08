package cloud.deuterium.maxbet.combat.config;

import cloud.deuterium.maxbet.combat.events.CombatCommandEvent;
import cloud.deuterium.maxbet.combat.events.CombatStartEvent;
import cloud.deuterium.maxbet.combat.events.CombatStopEvent;
import cloud.deuterium.maxbet.combat.events.CombatUpdateEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Created by Milan Stojkovic 26-Mar-2024
 */
@Configuration
public class SinkConfig {


    @Bean
    public Sinks.Many<CombatCommandEvent> combatCommandSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<CombatCommandEvent> combatCommandFlux(Sinks.Many<CombatCommandEvent> sink) {
        return sink.asFlux();
    }

    @Bean
    public Sinks.Many<CombatUpdateEvent> combatUpdateSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<CombatUpdateEvent> combatUpdateFlux(Sinks.Many<CombatUpdateEvent> sink) {
        return sink.asFlux();
    }

    @Bean
    public Sinks.Many<CombatStartEvent> combatStartSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<CombatStartEvent> combatStartFlux(Sinks.Many<CombatStartEvent> sink) {
        return sink.asFlux();
    }

    @Bean
    public Sinks.Many<CombatStopEvent> combatStopSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<CombatStopEvent> combatStopFlux(Sinks.Many<CombatStopEvent> sink) {
        return sink.asFlux();
    }
}
