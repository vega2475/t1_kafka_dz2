package ru.t1.java.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ru.t1.java.demo.model.Transaction}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {
    @NotNull
    @JsonProperty("amount")
    private BigDecimal amount;
    @NotNull
    @JsonProperty("client_id")
    private Long clientId;
    @NotNull
    @JsonProperty("account_id")
    private Long accountId;
}