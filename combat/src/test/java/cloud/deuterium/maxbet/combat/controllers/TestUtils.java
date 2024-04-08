package cloud.deuterium.maxbet.combat.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Milan Stojkovic 08-Apr-2024
 */
public class TestUtils {

    public static final String VALID_USER_JWT = "eyJraWQiOiI0MWQxNjA4MC1kNjJkLTQ2ODUtYjIxNy04ZTBkNGQwYmVkYjQiLCJhbGciOiJSUzI1NiJ9.eyJhcHAiOiJXb3JsZCBvZiBHYW1lcyIsInN1YiI6InVzZXJAd29nLmlvIiwiYXVkIjoid29nX3B1YmxpY19jbGllbnQiLCJuYmYiOjE3MTIyNzU1NTMsInNjb3BlIjpbIm9wZW5pZCJdLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4ODg4IiwiaWQiOiIyNDI1NTEyZC1jY2M5LTRjMjItYjRkMS05MmI4NTJjODdjZjYiLCJleHAiOjI3MTIyNzU4NTMsImlhdCI6MTcxMjI3NTU1MywianRpIjoiZmM1ZmQ4NDQtNGE2Yy00ZTBmLTkzNjQtNGI1Yjc2MmIxYTI3In0.MDgdMTrYF7OHBMSA4OPzxGdl4RduHphPQJDPHykRDfvX98IJjFuXtPdyB9Qz6z5CHyzIfvdOJG0LYdFnP16Av7g96dLSkavbohMGBpK8pwfyV1WMPAXF9I9lZt87I9LSIg3VzXW6YaEZGtM7ppQY9pultjpVhMACBRS-CY-1qbLUVjwsHwEi-laNS7ibePQtmSQa9ie_SEdyETNzUCmP1yXatYWzvTiAEgCq5o6suHsR1OPieVqXjLCOXx4PlTy16zwnoTpDid8s6bxGC5UDIC6Cf--FtfPjYPFLebPuvkrEvXhEdQgweEc51ZHwVh0sQ671iH9wWKdrc-BUp6mTNw";

    public static JwtAuthenticationToken getMockJwt() {
        Jwt jwt = new Jwt(VALID_USER_JWT,
                Instant.now(),
                Instant.now().plus(1, ChronoUnit.DAYS),
                Map.of("alg", "RS256"),
                Map.of("id", "23916d66-ce43-4fd0-a32a-6806fb6520ba"));
        final Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new JwtAuthenticationToken(jwt, authorities);
    }
}
