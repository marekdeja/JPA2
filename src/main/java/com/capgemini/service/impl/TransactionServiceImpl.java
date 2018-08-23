package com.capgemini.service.impl;

import com.capgemini.dao.PositionDao;
import com.capgemini.dao.ProductDao;
import com.capgemini.dao.TransactionDao;
import com.capgemini.dao.TransactionDaoSpringData;
import com.capgemini.domain.PositionEntity;
import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.Status;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.TransactionService;
import com.capgemini.types.PositionTO;
import com.capgemini.types.TransactionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionDaoSpringData transactionDaoSpringData;

    @Override
    public TransactionTO saveTransaction(TransactionTO transactionTO) {
        TransactionEntity transactionEntity = transactionDao.save(transactionMapper.toTransactionEntity(transactionTO));
        return TransactionMapper.toTransactionTO((transactionEntity));
    }

    @Override
    public void removeTransaction(Long id) {
        transactionDaoSpringData.removeById(id);
    }

    @Override
    public Collection<TransactionTO> findAll() {
        Collection<TransactionEntity> transactionFound = transactionDaoSpringData.findAll();
        return TransactionMapper.map2TOs(transactionFound);
    }

    @Override
    public TransactionTO findOne(Long id) {
        return TransactionMapper.toTransactionTO(transactionDaoSpringData.findOne(id));
    }

    /**
     * Checking condition for adding transaction.
     * 1. Customer who has less than 3 realized transactions is not allowed to:
     * a. do shopping for more than 5000zl
     * b. buy 5 identical products that price per item is over 7000
     * 2. Overall wage of products can not exceed 25 kg.
     * 3. TODO
     *
     * @param transactionTO
     * @return saved transactionTO
     */
    @Override
    public TransactionTO addTransaction(TransactionTO transactionTO) {


        if (checkThreeTransactions(transactionTO)) {
            return saveTransaction(transactionTO);
        }
        return null;
    }

    public boolean checkOrderLessThan5000(TransactionTO transactionTO) {

        Collection<Long> positionsIDs = transactionTO.getPositions();
        Float priceAll = 0.0f;
        for (Long position : positionsIDs) {
            PositionEntity positionEntity = positionDao.getOne(position);
            priceAll += (productDao.getOne(positionEntity.getId()).getPrice());
        }

        if ( priceAll > 5000) {
            throw new RuntimeException("You have too little realized transactions on your account to buy for over 5000zl");
        }
        return true;
    }

    public boolean checkThreeTransactions(TransactionTO transactionTO){
        Long customerId = transactionTO.getCustomer().getId();
        List<TransactionEntity> transactionsWithCustomerId = transactionDaoSpringData.findByCustomerId(customerId);

        List<TransactionEntity> transactionsWithCustomerIdRealized = new ArrayList<>();
        for (int i = 0; i < transactionsWithCustomerId.size(); i++) {
            if (transactionsWithCustomerId.get(i).getStatus() == Status.REALIZED) {
                transactionsWithCustomerIdRealized.add(transactionsWithCustomerId.get(i));
            }
        }
        if (transactionsWithCustomerIdRealized.size() < 3) {
            return false;
        }
        return true;
    }

}
