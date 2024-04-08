package cloud.deuterium.maxbet.character.repositories;

import cloud.deuterium.maxbet.character.entities.CharacterClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@Repository
public interface ClassRepository extends JpaRepository<CharacterClass, String> {
}
