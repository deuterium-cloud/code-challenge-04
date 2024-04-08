package cloud.deuterium.maxbet.character.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeResponse {

    private String challengerId;
    private String challengerUserId;
    private int challengerHealth;
    private int challengerMana;
    private int challengerAttack;
    private int challengerCast;
    private int challengerHeal;

    private String challengedId;
    private String challengedUserId;
    private int challengedHealth;
    private int challengedMana;
    private int challengedAttack;
    private int challengedCast;
    private int challengedHeal;
}
