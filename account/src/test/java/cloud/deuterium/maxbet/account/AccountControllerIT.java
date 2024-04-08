package cloud.deuterium.maxbet.account;

import cloud.deuterium.maxbet.account.dtos.CreateAccountDto;
import cloud.deuterium.maxbet.account.entities.Account;
import cloud.deuterium.maxbet.account.entities.Role;
import cloud.deuterium.maxbet.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

class AccountControllerIT extends BaseIT {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AccountRepository repository;

    @BeforeEach
    void seedDB() {
        repository.deleteAll();
        Account account = Account.builder()
                .email("master@example.com")
                .role(Role.ROLE_GAME_MASTER)
                .passwordHash(encoder.encode("super_secret"))
                .build();
        repository.save(account);
    }

    @DisplayName("Should return 409 because Account with same email already exists")
    @Test
    void create_account_failed() throws Exception {

        Optional<Account> optional = repository.findByEmail("master@example.com");
        assertThat(optional.isPresent()).isTrue();

        CreateAccountDto dto = new CreateAccountDto("master@example.com", "12345678");

        mockMvc.perform(post("/accounts")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict());
    }

    @DisplayName("Should create new Account")
    @Test
    void create_account_ok() throws Exception{

        Optional<Account> optional = repository.findByEmail("user@example.com");
        assertThat(optional.isEmpty()).isTrue();

        CreateAccountDto dto = new CreateAccountDto("user@example.com", "12345678");

        mockMvc.perform(post("/accounts")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));

        Optional<Account> optional1 = repository.findByEmail("user@example.com");
        assertThat(optional1.isPresent()).isTrue();
    }
}
