package cloud.deuterium.maxbet.combat.events;

import lombok.*;

/**
 * Created by Milan Stojkovic 07-Apr-2024
 */

@ToString
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WinnerEvent {
    private String winnerId;
    private String loserId;
}
