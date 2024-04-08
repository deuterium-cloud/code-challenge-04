package cloud.deuterium.maxbet.character.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "character_item")
public class CharacterItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "character_id")
    WoGCharacter character;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    Item item;
}
