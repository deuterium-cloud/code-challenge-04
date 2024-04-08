package cloud.deuterium.maxbet.character.repositories;

import cloud.deuterium.maxbet.character.entities.CharacterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, String> {
    List<CharacterItem> findAllByCharacter_Id(String characterId);
}
