package cloud.deuterium.maxbet.combat.entities;

import cloud.deuterium.maxbet.combat.enums.CombatStatus;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@ToString
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Combat {

    @Id
    private UUID id;

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

    private CombatStatus status;
}
