package cloud.deuterium.maxbet.character.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemDto {

    @NotBlank
    @Length(max = 255)
    private String name;
    @NotBlank
    @Length(max = 1023)
    private String description;
    private int bonusStrength;
    private int bonusAgility;
    private int bonusIntelligence;
    private int bonusFaith;
}
