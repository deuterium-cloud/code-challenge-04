package cloud.deuterium.maxbet.account.service;

import cloud.deuterium.maxbet.account.dtos.AccountCreatedDto;
import cloud.deuterium.maxbet.account.dtos.CreateAccountDto;
import cloud.deuterium.maxbet.account.entities.Account;
import cloud.deuterium.maxbet.account.entities.Role;
import cloud.deuterium.maxbet.account.exceptions.DuplicateException;
import cloud.deuterium.maxbet.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    public AccountCreatedDto create(CreateAccountDto accountDto) {

        boolean existsByEmail = repository.existsByEmail(accountDto.getEmail());
        if (existsByEmail) {
            throw new DuplicateException("Account with given email: " + accountDto.getEmail() + " already exists");
        }

        Account account = Account.builder()
                .email(accountDto.getEmail())
                .passwordHash(encoder.encode(accountDto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        Account saved = repository.save(account);

        return AccountCreatedDto.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .role(saved.getRole())
                .build();
    }
}
