package cloud.deuterium.maxbet.combat.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@ToString
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
