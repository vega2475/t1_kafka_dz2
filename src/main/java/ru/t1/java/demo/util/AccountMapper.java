package ru.t1.java.demo.util;

import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.dto.AccountDto;

@Component
public class AccountMapper {
    public static Account toEntity(AccountDto accountDto){
        return Account.builder()
                .accountType(accountDto.getAccountType())
                .clientId(accountDto.getClientId())
                .balance(accountDto.getBalance())
                .build();
    }
}
