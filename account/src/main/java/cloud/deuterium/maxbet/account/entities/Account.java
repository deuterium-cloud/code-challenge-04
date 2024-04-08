package cloud.deuterium.maxbet.account.entities;

import jakarta.persistence.*;
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
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String passwordHash;
}
