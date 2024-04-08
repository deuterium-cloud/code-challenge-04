package cloud.deuterium.maxbet.character.services;

import cloud.deuterium.maxbet.character.dtos.ChallengeRequest;
import cloud.deuterium.maxbet.character.dtos.ChallengeResponse;
import cloud.deuterium.maxbet.character.dtos.response.CharacterClassDto;
import cloud.deuterium.maxbet.character.dtos.response.CharacterDto;
import cloud.deuterium.maxbet.character.dtos.response.CharacterItemDto;
import cloud.deuterium.maxbet.character.dtos.request.CreateCharacterDto;
import cloud.deuterium.maxbet.character.entities.*;
import cloud.deuterium.maxbet.character.exceptions.DuplicateException;
import cloud.deuterium.maxbet.character.exceptions.NotAuthorizedException;
import cloud.deuterium.maxbet.character.exceptions.NotFoundException;
import cloud.deuterium.maxbet.character.repositories.CharacterRepository;
import cloud.deuterium.maxbet.character.repositories.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@RequiredArgsConstructor
@Service
public class CharacterService {

    private final CharacterRepository repository;
    private final ClassRepository classRepository;
    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    public Page<CharacterView> getAll(Pageable pageable) {
        return repository.getCharacters(pageable);
    }

    @Transactional(readOnly = true)
    public CharacterDto getById(String id) {

        WoGCharacter character = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Character with id=" + id));

        return calculateStats(character);
    }

    @Transactional
    public CharacterDto save(CreateCharacterDto character, String accountId) {

        boolean existsByName = repository.existsByName(character.getName());
        if (existsByName) {
            throw new DuplicateException("Character with name: " + character.getName() + " already exists");
        }

        CharacterClass characterClass = classRepository.findById(character.getClassId())
                .orElseThrow(() -> new NotFoundException("No Class with id=" + character.getClassId()));

        WoGCharacter woGCharacter = mapper.map(character, WoGCharacter.class);

        woGCharacter.setCharacterClass(characterClass);
        woGCharacter.setCreatedBy(accountId);
        WoGCharacter saved = repository.save(woGCharacter);

        return calculateStats(saved);
    }

    public ChallengeResponse challenge(ChallengeRequest cRequest, String accountId) {

        WoGCharacter challenger = repository.findByIdAndCreatedBy(cRequest.getChallengerId(), accountId)
                .orElseThrow(() -> new NotAuthorizedException(
                        "Not Authorized for Challenger with id=" + cRequest.getChallengerId()));

        WoGCharacter challenged = repository.findById(cRequest.getChallengedId())
                .orElseThrow(() -> new NotFoundException("Not found Character with id=" + cRequest.getChallengedId()));

        CharacterDto challengerStats = calculateStats(challenger);
        CharacterDto challengedStats = calculateStats(challenged);

        return getChallengeResponse(challengerStats, challengedStats);
    }

    private static ChallengeResponse getChallengeResponse(CharacterDto challenger, CharacterDto challenged) {
        return ChallengeResponse.builder()
                .challengerId(challenger.getId())
                .challengerUserId(challenger.getCreatedBy())
                .challengerHealth(challenger.getHealth())
                .challengerMana(challenger.getMana())

                .challengerAttack(challenger.getStrength() + challenger.getAgility())
                .challengerCast(2 * challenger.getIntelligence())
                .challengerHeal(challenger.getFaith())

                .challengedId(challenged.getId())
                .challengedUserId(challenged.getCreatedBy())
                .challengedHealth(challenged.getHealth())
                .challengedMana(challenged.getMana())

                .challengedAttack(challenged.getStrength() + challenged.getAgility())
                .challengedCast(2 * challenged.getIntelligence())
                .challengedHeal(challenged.getFaith())
                .build();
    }

    private CharacterDto calculateStats(WoGCharacter character) {
        Set<CharacterItem> items = character.getItems();

        return CharacterDto.builder()
                .id(character.getId())
                .name(character.getName())
                .health(character.getHealth())
                .mana(character.getMana())
                .createdBy(character.getCreatedBy())
                .strength(character.getBaseStrength() + getBonusSum(items, Item::getBonusStrength))
                .agility(character.getBaseAgility() + getBonusSum(items, Item::getBonusAgility))
                .intelligence(character.getBaseIntelligence() + getBonusSum(items, Item::getBonusIntelligence))
                .faith(character.getBaseFaith() + getBonusSum(items, Item::getBonusFaith))
                .characterClass(mapper.map(character.getCharacterClass(), CharacterClassDto.class))
                .items(items.stream()
                        .map(ci -> mapper.map(ci, CharacterItemDto.class))
                        .collect(Collectors.toSet()))
                .build();
    }

    private static int getBonusSum(Set<CharacterItem> items, ToIntFunction<Item> function) {
        return items.stream()
                .map(CharacterItem::getItem)
                .mapToInt(function)
                .sum();
    }
}
