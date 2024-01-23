package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if(account.getUsername() == null || account.getUsername().trim().isEmpty() || account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        if(accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null;
            
        }

        return accountRepository.save(account);
    }
    
    public Optional<Account> login(String username, String password) {
        if(username == null || username.trim().isEmpty() || password == null) {
            return Optional.empty();
        }

        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent() && account.get().getPassword().equals(password)) {
            return account;
        } else {
            return Optional.empty();
        }
    }
    
}
