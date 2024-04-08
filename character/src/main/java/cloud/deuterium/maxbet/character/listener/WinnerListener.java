package cloud.deuterium.maxbet.character.listener;

import cloud.deuterium.maxbet.character.dtos.events.WinnerEvent;
import cloud.deuterium.maxbet.character.entities.CharacterItem;
import cloud.deuterium.maxbet.character.entities.WoGCharacter;
import cloud.deuterium.maxbet.character.repositories.CharacterItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

/**
 * Created by Milan Stojkovic 07-Apr-2024
 */

@Slf4j
@Configuration
public class WinnerListener {

    @Bean
    public Consumer<WinnerEvent> onCombatStopEvent(CharacterItemRepository repository,
                                                   CacheManager cacheManager) {
        return we -> {

            log.info("Consume Winner Event -> {}", we);

            List<CharacterItem> items = repository.findAllByCharacter_Id(we.getLoserId());

            getRandomItem(items)
                    .ifPresent(item -> {
                        item.setCharacter(new WoGCharacter(we.getWinnerId()));
                        repository.save(item);
                    });

            Cache characters = cacheManager.getCache("characters");
            if (nonNull(characters)) {
                log.info("Evict cache for characters -> {}", we);
                characters.evictIfPresent(we.getWinnerId());
                characters.evictIfPresent(we.getLoserId());
            }
        };
    }

    public static Optional<CharacterItem> getRandomItem(Collection<CharacterItem> items) {
        if (items.isEmpty()) {
            return Optional.empty();
        }
        int anInt = ThreadLocalRandom.current()
                .nextInt(items.size());
        return Optional.of(new ArrayList<>(items).get(anInt));
    }


}
