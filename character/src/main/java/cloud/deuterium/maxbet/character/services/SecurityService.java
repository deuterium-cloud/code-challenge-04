package cloud.deuterium.maxbet.character.services;

import cloud.deuterium.maxbet.character.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@RequiredArgsConstructor
@Service
public class SecurityService {

    private final CharacterRepository repository;

    public boolean isResourceOwner(Authentication authentication, String characterId) {
        return repository.existsByIdAndCreatedBy(characterId, extractAccountId(authentication));
    }

    public String extractAccountId(Authentication authentication) {

        if (nonNull(authentication) && authentication.getPrincipal() instanceof Jwt jwt) {
            Object id = jwt.getClaims()
                    .get("id");
            if (isNull(id)) return null;
            return id.toString();
        }
        return null;
    }
}
