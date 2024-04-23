package cloud.deuterium.maxbet.character.repositories;

import cloud.deuterium.maxbet.character.entities.CharacterView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cloud.deuterium.maxbet.character.entities.WoGCharacter;

import java.util.Optional;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@Repository
public interface CharacterRepository extends JpaRepository<WoGCharacter, String> {
    @Query("SELECT c.name AS name, c.health AS health, c.mana AS mana FROM WoGCharacter c")
    Page<CharacterView> getCharacters(Pageable pageable);

    boolean existsByName(String name);

    boolean existsByIdAndCreatedBy(String id, String createdBy);

    Optional<WoGCharacter> findByIdAndCreatedBy(String id, String createdBy);
}
