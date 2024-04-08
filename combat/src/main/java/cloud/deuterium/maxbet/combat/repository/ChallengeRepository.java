package cloud.deuterium.maxbet.combat.repository;

import cloud.deuterium.maxbet.combat.entities.Combat;
import cloud.deuterium.maxbet.combat.enums.CombatStatus;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */

@Repository
public interface ChallengeRepository extends R2dbcRepository<Combat, UUID> {

    @Modifying
    @Query("UPDATE Combat SET status = :status WHERE id = :id")
    Mono<Void> updateCombatStatus(@Param(value = "id") UUID id, @Param(value = "status") CombatStatus status);

    @Query("""
            SELECT COUNT(1) = 0 FROM Combat WHERE 
            status = :status AND 
            (challenger_id = :challengerId OR challenged_id = :challengedId OR 
             challenger_id = :challengedId OR challenged_id = :challengerId)                         
            """)
    Mono<Boolean> isCharactersAvailable(@Param(value = "status") CombatStatus status,
                                        @Param(value = "challengerId") String challengerId,
                                        @Param(value = "challengedId") String challengedId);

    @Query("""
            SELECT COUNT(1) > 0 FROM Combat WHERE 
            status = :status AND id = :combatId AND
            (challenger_user_id = :userId OR challenged_user_id = :userId)                         
            """)
    Mono<Boolean> isResourceOwner(@Param(value = "status") CombatStatus status,
                                  @Param(value = "combatId") UUID combatId,
                                  @Param(value = "userId") String userId);
}
