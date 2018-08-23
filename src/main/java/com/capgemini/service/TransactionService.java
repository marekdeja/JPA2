package com.capgemini.service;

import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.TransactionTO;

import java.util.Collection;
import java.util.List;

public interface TransactionService {

    TransactionTO saveTransaction(TransactionTO transactionTO);
    void removeTransaction(Long id);
    Collection<TransactionTO> findAll();
    TransactionTO findOne(Long id);

    TransactionTO addTransaction(TransactionTO transactionTO);
}
