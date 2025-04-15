package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account registerAccount(Account account){
        if(account.getUsername().equals("") || account.getPassword().length() < 4){
            return null;
        }

        if(accountRepository.findByUsername(account.getUsername()) != null){
            account.setAccountId(-1);
            return account;
        }

        accountRepository.InsertAccountIntoTable(account.getUsername(), account.getPassword());
        return accountRepository.findByUsername(account.getUsername());
    }

    public Account logintoAccount(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

    public boolean accountExists(Integer accountId){
        if (accountRepository.findByAccountId(accountId) != null){
            return true;
        } else{
            return false;
        }
    }

    public boolean accountExists(String username){
        if (accountRepository.findByUsername(username) != null){
            return true;
        } else{
            return false;
        }
    }
}
