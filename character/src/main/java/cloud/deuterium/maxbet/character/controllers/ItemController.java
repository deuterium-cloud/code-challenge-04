package cloud.deuterium.maxbet.character.controllers;

import cloud.deuterium.maxbet.character.dtos.request.CreateItemDto;
import cloud.deuterium.maxbet.character.dtos.request.GiftItemDto;
import cloud.deuterium.maxbet.character.dtos.request.GrantItemDto;
import cloud.deuterium.maxbet.character.dtos.response.ItemDto;
import cloud.deuterium.maxbet.character.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    @PreAuthorize("hasRole('GAME_MASTER')")
    @GetMapping
    public Page<ItemDto> getAll(@PageableDefault Pageable pageable) {
        log.info("Return all Items");
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ItemDto getByID(@PathVariable String id) {
        log.info("Get Item with id={}", id);
        return service.getById(id);
    }

    @PreAuthorize("hasRole('GAME_MASTER')")
    @PostMapping
    public ItemDto create(@RequestBody @Valid CreateItemDto item) {
        log.info("Create new Item with name: {}", item.getName());
        return service.create(item);
    }

    @CacheEvict(value = "characters", key = "#grantItem.characterId")
    @PostMapping("/grant")
    public void grant(@RequestBody @Valid GrantItemDto grantItem) {
        log.info("Grant Item with id: {} to Character: {}", grantItem.getItemId(), grantItem.getCharacterId());
        service.grant(grantItem);
    }

    @CacheEvict(value = "characters", key = "#giftItem.characterId")
    @PostMapping("/gift")
    public void gift(@RequestBody @Valid GiftItemDto giftItem) {
        log.info("Gift Item with characterItemId: {} to Character: {}", giftItem.getCharacterItemId(), giftItem.getCharacterId());
        service.gift(giftItem);
    }
}
