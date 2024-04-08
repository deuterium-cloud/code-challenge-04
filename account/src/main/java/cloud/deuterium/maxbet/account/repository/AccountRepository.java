package cloud.deuterium.maxbet.account.repository;

import cloud.deuterium.maxbet.account.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);
}
