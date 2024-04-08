package cloud.deuterium.maxbet.character.controllers;

import cloud.deuterium.maxbet.character.dtos.ChallengeRequest;
import cloud.deuterium.maxbet.character.dtos.ChallengeResponse;
import cloud.deuterium.maxbet.character.dtos.response.CharacterDto;
import cloud.deuterium.maxbet.character.dtos.request.CreateCharacterDto;
import cloud.deuterium.maxbet.character.entities.CharacterView;
import cloud.deuterium.maxbet.character.services.CharacterService;
import cloud.deuterium.maxbet.character.services.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService service;
    private final SecurityService securityService;

    @PreAuthorize("hasRole('GAME_MASTER')")
    @GetMapping
    public Page<CharacterView> getAll(@PageableDefault Pageable pageable) {
        log.info("Return all Characters");
        return service.getAll(pageable);
    }

    @Cacheable(value = "characters", key = "#id")
    @PreAuthorize("hasRole('GAME_MASTER') or @securityService.isResourceOwner(authentication, #id)")
    @GetMapping("/{id}")
    public CharacterDto getById(@PathVariable String id) {
        log.info("Return Character with id={}", id);
        return service.getById(id);
    }

    @PostMapping
    public CharacterDto save(@RequestBody CreateCharacterDto character, Authentication authentication) {
        log.info("Create Character with name: {}", character.getName());
        return service.save(character, securityService.extractAccountId(authentication));
    }

    @PostMapping("/challenge")
    public ChallengeResponse challenge(@RequestBody ChallengeRequest cRequest, Authentication authentication) {
        log.info("Challenge request: {} VS {}", cRequest.getChallengedId(), cRequest.getChallengedId());
        return service.challenge(cRequest, securityService.extractAccountId(authentication));
    }
}
