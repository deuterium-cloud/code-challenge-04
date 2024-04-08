package cloud.deuterium.maxbet.combat.service;

import cloud.deuterium.maxbet.combat.enums.Action;
import cloud.deuterium.maxbet.combat.events.CombatCommandEvent;
import cloud.deuterium.maxbet.combat.events.CombatStartEvent;
import cloud.deuterium.maxbet.combat.events.CombatStopEvent;
import cloud.deuterium.maxbet.combat.events.CombatUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.nonNull;

/**
 * Created by Milan Stojkovic 06-Apr-2024
 */

@RequiredArgsConstructor
@Slf4j
@Service
public class BroadcasterService {

    @Value("${app.combat.termination.after-minutes}")
    private int terminateAfterMinutes;

    private final Sinks.Many<CombatUpdateEvent> combatUpdateSink;
    private final Sinks.Many<CombatStopEvent> combatStopSink;

    private final Map<UUID, Tuple2<Action, Disposable>> actions = new HashMap<>();
    private final Map<UUID, Disposable> terminators = new HashMap<>();

    public CombatCommandEvent process(CombatCommandEvent event) {

        Tuple2<Action, Disposable> t2 = actions.get(event.getCombatId());

        if (nonNull(t2) && event.getAction().equals(t2.getT1())) {
            log.debug("Same Action: {} for {}", event.getAction(), event.getCombatId());
            return event;
        }

        if (nonNull(t2)) {
            log.debug("Dispose for new Action {} for {}", event.getAction(), event.getCombatId());
            t2.getT2().dispose();
        }

        Flux<Sinks.EmitResult> eventFlux = Flux.interval(Duration.ofSeconds(event.getAction().getTime()))
                .map(sequence -> combatUpdateSink.tryEmitNext(new CombatUpdateEvent(event)));

        Disposable disposable = eventFlux.subscribe();

        actions.put(event.getCombatId(), Tuples.of(event.getAction(), disposable));

        return event;
    }

    public CombatStopEvent stopBroadcast(CombatStopEvent event) {

        log.debug("Stopping the Broadcast for {}", event.combatId());

        Tuple2<Action, Disposable> t2 = actions.get(event.combatId());

        if (nonNull(t2)) {
            t2.getT2().dispose();
        }

        actions.remove(event.combatId());
        terminators.remove(event.combatId());

        return event;
    }

    public CombatStartEvent addTerminator(CombatStartEvent event) {
        log.debug("Add terminator for {}", event.combatId());
        Flux<Sinks.EmitResult> eventFlux = Flux.interval(Duration.ofMinutes(terminateAfterMinutes))
                .take(1)
                .map(sequence -> combatStopSink.tryEmitNext(new CombatStopEvent(event.combatId())));

        Disposable disposable = eventFlux.subscribe();

        terminators.put(event.combatId(), disposable);

        return event;
    }
}
