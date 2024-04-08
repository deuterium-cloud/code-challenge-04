package cloud.deuterium.maxbet.combat.events;

import cloud.deuterium.maxbet.combat.enums.Action;
import lombok.*;

import java.util.UUID;

/**
 * Created by Milan Stojkovic 06-Apr-2024
 */

@ToString
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CombatCommandEvent {
    private UUID combatId;
    private String userId;
    private Action action;
}
