package cloud.deuterium.maxbet.account.service;

import cloud.deuterium.maxbet.account.entities.Account;
import cloud.deuterium.maxbet.account.entities.WoGUserDetails;
import cloud.deuterium.maxbet.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.warn("Login with email: {}", email);
        Account account = repository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials!"));

        return new WoGUserDetails(account);
    }
}
