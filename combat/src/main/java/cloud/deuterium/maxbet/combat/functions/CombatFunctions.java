package cloud.deuterium.maxbet.combat.functions;

import cloud.deuterium.maxbet.combat.events.CombatStopEvent;
import cloud.deuterium.maxbet.combat.events.WinnerEvent;
import cloud.deuterium.maxbet.combat.service.BroadcasterService;
import cloud.deuterium.maxbet.combat.service.UpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * Created by Milan Stojkovic 07-Apr-2024
 */

@Slf4j
@Configuration
public class CombatFunctions {

    @Bean
    public Function<Flux<CombatStopEvent>, Flux<WinnerEvent>> combatStopEventProcessor(BroadcasterService broadcaster,
                                                                                       UpdateService service) {
        return flux -> flux
                .doOnNext(event -> log.debug("Consuming Combat Stop Event {}", event.combatId()))
                .map(broadcaster::stopBroadcast)
                .flatMap(service::updateStatus)
                .flatMap(service::createWinnerEvent)
                .doOnError(e -> log.error("Combat Stop Error: {}", e.getMessage()));
    }
}
