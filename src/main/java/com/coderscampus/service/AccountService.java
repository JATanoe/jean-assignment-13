package com.coderscampus.service;

import com.coderscampus.domain.Account;
import com.coderscampus.repository.AccountRepository;
import com.coderscampus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepo;

    @Autowired
    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account findById(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account #" + accountId + " not found"));
    }

}
