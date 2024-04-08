package cloud.deuterium.maxbet.account.dtos;

import cloud.deuterium.maxbet.account.entities.Role;
import lombok.*;

import java.util.UUID;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreatedDto {
    private UUID id;
    private String email;
    private Role role;
}
