package cloud.deuterium.maxbet.character.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterItemDto {
    private String id;
    private ItemDto item;
}
