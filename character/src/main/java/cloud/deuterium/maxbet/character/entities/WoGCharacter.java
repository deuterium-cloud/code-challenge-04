package cloud.deuterium.maxbet.character.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "character")
public class WoGCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String name;
    private int health;
    private int mana;
    @Column(name = "base_strength")
    private int baseStrength;
    @Column(name = "base_agility")
    private int baseAgility;
    @Column(name = "base_intelligence")
    private int baseIntelligence;
    @Column(name = "base_faith")
    private int baseFaith;
    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "character_class_id")
    private CharacterClass characterClass;

    @OneToMany(mappedBy = "character")
    private Set<CharacterItem> items = new HashSet<>();

    public WoGCharacter(String id) {
        this.id = id;
    }
}
