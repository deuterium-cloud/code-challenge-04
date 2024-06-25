package cloud.deuterium.maxbet.combat.controllers.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Milan Stojkovic 25-Jun-2024
 */
public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomClaim> {

    @Override
    public SecurityContext createSecurityContext(WithCustomClaim annotation) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Authentication authentication = getMockJwt(annotation.name(), annotation.value());

        context.setAuthentication(authentication);

        return context;
    }

    private JwtAuthenticationToken getMockJwt(String claimName, String claimValue) {

        Jwt jwt = new Jwt("placeholder",
                Instant.now(),
                Instant.now().plus(1, ChronoUnit.DAYS),
                Map.of("alg", "RS256"),
                Map.of(claimName, claimValue));

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new JwtAuthenticationToken(jwt, authorities);
    }

}
