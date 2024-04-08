package cloud.deuterium.maxbet.character.dtos.response;

import lombok.*;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private String id;
    private String name;
    private String description;
    private int bonusStrength;
    private int bonusAgility;
    private int bonusIntelligence;
    private int bonusFaith;
}
