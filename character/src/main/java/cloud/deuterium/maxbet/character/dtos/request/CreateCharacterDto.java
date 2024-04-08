package cloud.deuterium.maxbet.character.dtos.request;

import jakarta.validation.constraints.Min;
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
public class CreateCharacterDto {

    @NotBlank
    @Length(max = 255)
    private String name;
    @Min(0)
    private int health;
    @Min(0)
    private int mana;
    @Min(0)
    private int strength;
    @Min(0)
    private int agility;
    @Min(0)
    private int intelligence;
    @Min(0)
    private int faith;
    @NotBlank
    private String classId;
}
