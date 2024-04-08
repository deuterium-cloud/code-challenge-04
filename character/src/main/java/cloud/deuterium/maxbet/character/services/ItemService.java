package cloud.deuterium.maxbet.character.services;

import cloud.deuterium.maxbet.character.dtos.request.CreateItemDto;
import cloud.deuterium.maxbet.character.dtos.request.GiftItemDto;
import cloud.deuterium.maxbet.character.dtos.request.GrantItemDto;
import cloud.deuterium.maxbet.character.dtos.response.ItemDto;
import cloud.deuterium.maxbet.character.entities.CharacterItem;
import cloud.deuterium.maxbet.character.entities.Item;
import cloud.deuterium.maxbet.character.entities.WoGCharacter;
import cloud.deuterium.maxbet.character.exceptions.NotFoundException;
import cloud.deuterium.maxbet.character.repositories.CharacterItemRepository;
import cloud.deuterium.maxbet.character.repositories.CharacterRepository;
import cloud.deuterium.maxbet.character.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository repository;
    private final CharacterRepository characterRepository;
    private final CharacterItemRepository characterItemRepository;
    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    public Page<ItemDto> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(item -> mapper.map(item, ItemDto.class));
    }

    @Transactional(readOnly = true)
    public ItemDto getById(String id) {

        Item item = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Item with id=" + id));
        ItemDto dto = mapper.map(item, ItemDto.class);
        String suffix = getSuffix(item);

        dto.setName(item.getName() + suffix);

        return dto;
    }

    @Transactional
    public ItemDto create(CreateItemDto item) {
        Item mapped = mapper.map(item, Item.class);
        return mapper.map(repository.save(mapped), ItemDto.class);
    }

    @Transactional
    public void grant(GrantItemDto grantItem) {
        Item item = repository.findById(grantItem.getItemId())
                .orElseThrow(() -> new NotFoundException("No Item with id=" + grantItem.getItemId()));

        WoGCharacter woGCharacter = characterRepository.findById(grantItem.getCharacterId())
                .orElseThrow(() -> new NotFoundException("No Character with id=" + grantItem.getItemId()));

        characterItemRepository.save(new CharacterItem(null, woGCharacter, item));
    }

    @Transactional
    public void gift(GiftItemDto giftItem) {
        WoGCharacter woGCharacter = characterRepository.findById(giftItem.getCharacterId())
                .orElseThrow(() -> new NotFoundException("No Character with id=" + giftItem.getCharacterId()));

        CharacterItem characterItem = characterItemRepository.findById(giftItem.getCharacterItemId())
                .orElseThrow(() -> new NotFoundException("No CharacterItem with id=" + giftItem.getCharacterId()));

        characterItem.setCharacter(woGCharacter);
        characterItemRepository.save(characterItem);
    }

    private String getSuffix(Item item) {

        Pair pair = Stream.of(
                        new Pair(item.getBonusStrength(), " Of The Bear"),
                        new Pair(item.getBonusAgility(), " Of The Cobra"),
                        new Pair(item.getBonusFaith(), " Of The Unicorn"),
                        new Pair(item.getBonusIntelligence(), " Of The Owl")
                )
                .max(Comparator.comparing(Pair::value))
                .orElseThrow(NoSuchElementException::new);

        return pair.suffix();
    }

    private record Pair(int value, String suffix) {
    }

}
