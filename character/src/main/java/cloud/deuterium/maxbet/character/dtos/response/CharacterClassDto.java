package cloud.deuterium.maxbet.character.dtos.response;

import lombok.*;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterClassDto {
    private String id;
    private String name;
    private String description;
}
