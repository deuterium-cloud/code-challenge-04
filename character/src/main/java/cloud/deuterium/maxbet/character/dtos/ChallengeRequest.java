package cloud.deuterium.maxbet.character.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeRequest {
    @NotBlank
    private String challengerId;
    @NotBlank
    private String challengedId;
}
