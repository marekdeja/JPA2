package com.capgemini.service.impl;

import com.capgemini.dao.TransactionDao;
import com.capgemini.dao.TransactionDaoSpringData;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.TransactionService;
import com.capgemini.types.TransactionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionDaoSpringData transactionDaoSpringData;

    @Override
    public TransactionTO saveTransaction (TransactionTO transactionTO){
        TransactionEntity transactionEntity = transactionDao.save(transactionMapper.toTransactionEntity(transactionTO));
        return TransactionMapper.toTransactionTO((transactionEntity));
    }

    @Override
    public void removeTransaction (Long id){
        transactionDaoSpringData.removeById(id);
    }

    @Override
    public Collection<TransactionTO> findAll(){
        Collection<TransactionEntity> transactionFound = transactionDaoSpringData.findAll();
        return TransactionMapper.map2TOs(transactionFound);
    }

    @Override
    public TransactionTO findOne(Long id){
        return TransactionMapper.toTransactionTO(transactionDaoSpringData.findOne(id));
    }

    @Override
    public TransactionTO addTransaction(TransactionTO transactionTO){
        Long customerId = transactionTO.getId();
        List<TransactionEntity> transactionsWithCustomerId = transactionDaoSpringData.findByCustomerId(customerId);

        Integer priceAll = transactionTO.getAmount();

        if (transactionsWithCustomerId.size()<3 && transactionTO.getAmount()>5000){
            throw new RuntimeException("You have too little realized transactions on your account to buy for over 5000zl");
        }

       // if (transactionTO.)

        return saveTransaction(transactionTO);
    }

}
