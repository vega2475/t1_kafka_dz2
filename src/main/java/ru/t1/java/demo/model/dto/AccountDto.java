package ru.t1.java.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.t1.java.demo.model.AccountType;
import ru.t1.java.demo.model.Client;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {
    @JsonProperty("client_id")
    private Long clientId;

    @JsonProperty("account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonProperty("balance")
    private Double balance;
}