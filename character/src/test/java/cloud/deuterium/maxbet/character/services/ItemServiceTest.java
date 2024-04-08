package cloud.deuterium.maxbet.character.services;

import cloud.deuterium.maxbet.character.dtos.response.ItemDto;
import cloud.deuterium.maxbet.character.entities.Item;
import cloud.deuterium.maxbet.character.repositories.CharacterItemRepository;
import cloud.deuterium.maxbet.character.repositories.CharacterRepository;
import cloud.deuterium.maxbet.character.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository repository;
    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private CharacterItemRepository characterItemRepository;

    private ItemService service;

    @BeforeEach
    void setUp() {
        this.service = new ItemService(repository, characterRepository, characterItemRepository, new ModelMapper());
    }

    @DisplayName("Should return Item with changed name")
    @ParameterizedTest
    @MethodSource("provideItems")
    void get_item_by_id(Item item, String suffix) {

        when(repository.findById(anyString())).thenReturn(Optional.of(item));

        ItemDto itemDto = service.getById("8d1134c7-57e8-43ec-965d-abb7efd31047");

        verify(repository, times(1)).findById("8d1134c7-57e8-43ec-965d-abb7efd31047");

        assertThat(itemDto.getName()).isEqualTo(item.getName() + suffix);
    }

    private static Stream<Arguments> provideItems() {
        return Stream.of(
                Arguments.of(new Item("8d1134c7-57e8-43ec-965d-abb7efd31047", "Sword", "Sword description", 50, 10, 10, 20), " Of The Bear"),
                Arguments.of(new Item("8d1134c7-57e8-43ec-965d-abb7efd31047", "Bow", "Bow description", 10, 50, 10, 20), " Of The Cobra"),
                Arguments.of(new Item("8d1134c7-57e8-43ec-965d-abb7efd31047", "Cloak", "Cloak description", 10, 10, 50, 20), " Of The Owl"),
                Arguments.of(new Item("8d1134c7-57e8-43ec-965d-abb7efd31047", "Spear", "Spear description", 20, 10, 30, 50), " Of The Unicorn")
        );
    }
}
