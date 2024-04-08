package cloud.deuterium.maxbet.character.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false, length = 1023)
    private String description;
    @Column(name = "bonus_strength")
    private int bonusStrength;
    @Column(name = "bonus_agility")
    private int bonusAgility;
    @Column(name = "bonus_intelligence")
    private int bonusIntelligence;
    @Column(name = "bonus_faith")
    private int bonusFaith;
}
