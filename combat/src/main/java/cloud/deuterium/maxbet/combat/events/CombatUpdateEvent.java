package cloud.deuterium.maxbet.combat.events;

import cloud.deuterium.maxbet.combat.enums.Action;
import lombok.*;

import java.util.UUID;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */

@ToString
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CombatUpdateEvent {
    private UUID combatId;
    private String userId;
    private Action action;

    public CombatUpdateEvent(CombatCommandEvent event) {
        this.combatId = event.getCombatId();
        this.userId = event.getUserId();
        this.action = event.getAction();
    }
}
