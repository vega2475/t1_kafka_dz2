package ru.t1.java.demo.service;

import ru.t1.java.demo.model.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransactions(List<Transaction> transactionList);
}
