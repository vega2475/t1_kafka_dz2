package ru.t1.java.demo.service;

import ru.t1.java.demo.model.Account;

import java.util.List;

public interface AccountService {
    void saveAccounts(List<Account> accounts);
}
