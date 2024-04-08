package cloud.deuterium.maxbet.character.dtos.response;

import lombok.*;

import java.util.Set;

/**
 * Created by Milan Stojkovic 03-Apr-2024
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDto {
    private String id;
    private String name;
    private int health;
    private int mana;
    private int strength;
    private int agility;
    private int intelligence;
    private int faith;
    private String createdBy;
    private CharacterClassDto characterClass;
    private Set<CharacterItemDto> items;
}
