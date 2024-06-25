package cloud.deuterium.maxbet.combat.controllers;

import cloud.deuterium.maxbet.combat.config.SecurityConfig;
import cloud.deuterium.maxbet.combat.controllers.utils.WithCustomClaim;
import cloud.deuterium.maxbet.combat.dtos.ChallengeRequest;
import cloud.deuterium.maxbet.combat.service.CombatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

/**
 * Created by Milan Stojkovic 07-Apr-2024
 */
@Import(SecurityConfig.class)
@WebFluxTest(controllers = CombatController.class)
class CombatControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    CombatService service;

    @MockBean
    ReactiveJwtDecoder jwtDecoder;


    @Test
    @DisplayName("Should return 401 Unauthorized")
    void challenge_failed() {
        ChallengeRequest request = new ChallengeRequest("6e928d7d-d0c6-4e0d-b3ed-170e37e38edd",
                "d112164e-6aae-4c3a-8a60-44f1a7270166");

        webClient.post()
                .uri("/api/challenge")
                .body(Mono.just(request), ChallengeRequest.class)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("Should return UUID of created Combat")
    void challenge() {
        when(service.challenge(any(ChallengeRequest.class), any(Jwt.class)))
                .thenReturn(Mono.just(UUID.fromString("d98656a7-78c1-489a-bac2-4a836bcac912")));

        ChallengeRequest request = new ChallengeRequest("6e928d7d-d0c6-4e0d-b3ed-170e37e38edd",
                "d112164e-6aae-4c3a-8a60-44f1a7270166");

        webClient.mutateWith(mockJwt())
                .post()
                .uri("/api/challenge")
                .body(Mono.just(request), ChallengeRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Should return 401 Unauthorized")
    void attack_failed() {
        webClient.post()
                .uri("/api/challenges/d98656a7-78c1-489a-bac2-4a836bcac912/attack")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void attack() {
        when(service.emitCommandEvent(any(UUID.class), any(String.class), any()))
                .thenReturn(Mono.empty());

        webClient.mutateWith(mockJwt().jwt(jwt -> jwt.claim("id", "23916d66-ce43-4fd0-a32a-6806fb6520ba")))
                .post()
                .uri("/api/challenge/d98656a7-78c1-489a-bac2-4a836bcac912/attack")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void cast_failed() {
        webClient.post()
                .uri("/api/challenge/d98656a7-78c1-489a-bac2-4a836bcac912/cast")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void cast() {
        when(service.emitCommandEvent(any(UUID.class), any(String.class), any()))
                .thenReturn(Mono.empty());

        webClient.mutateWith(mockJwt().jwt(jwt -> jwt.claim("id", "23916d66-ce43-4fd0-a32a-6806fb6520ba")))
                .post()
                .uri("/api/challenge/d98656a7-78c1-489a-bac2-4a836bcac912/cast")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void heal_failed() {
        webClient.post()
                .uri("/api/challenge/d98656a7-78c1-489a-bac2-4a836bcac912/heal")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithCustomClaim(name = "id", value = "23916d66-ce43-4fd0-a32a-6806fb6520ba")
    void heal() {
        when(service.emitCommandEvent(any(UUID.class), any(String.class), any()))
                .thenReturn(Mono.empty());

//        webClient.mutateWith(mockJwt().jwt(jwt -> jwt.claim("id", "23916d66-ce43-4fd0-a32a-6806fb6520ba")))
        webClient
                .post()
                .uri("/api/challenge/d98656a7-78c1-489a-bac2-4a836bcac912/heal")
                .exchange()
                .expectStatus().isOk();
    }

}
