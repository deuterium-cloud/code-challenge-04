package cloud.deuterium.maxbet.combat.enums;

import lombok.Getter;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */

@Getter
public enum Action {

    ATTACK(1),
    CAST(2),
    HEAL(2);

    private final int time;

    Action(int time) {
        this.time = time;
    }
}
