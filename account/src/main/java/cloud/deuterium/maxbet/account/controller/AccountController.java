package cloud.deuterium.maxbet.account.controller;

import cloud.deuterium.maxbet.account.dtos.AccountCreatedDto;
import cloud.deuterium.maxbet.account.dtos.CreateAccountDto;
import cloud.deuterium.maxbet.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Milan Stojkovic 02-Apr-2024
 */

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    @PostMapping
    public AccountCreatedDto createAccount(@RequestBody @Valid CreateAccountDto accountDto){
        log.info("Create account with email: {}", accountDto.getEmail());
        return service.create(accountDto);
    }
}
