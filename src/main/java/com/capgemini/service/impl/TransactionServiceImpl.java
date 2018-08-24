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
     * 1. Overall wage of products can not exceed 25 kg.
     *  2. Customer who has less than 3 realized transactions is not allowed to:
     *      a. do shopping for more than 5000zl
     *      b. buy 5 identical products that price per item is over 7000
     *
     *
     * @param transactionTO
     * @return saved transactionTO
     */
    @Override
    public TransactionTO addTransaction(TransactionTO transactionTO) {
        if (checkWageNotOver(transactionTO)) {
        }

            if (checkThreeTransactions(transactionTO)) {
                if (checkOrderLessThan5000(transactionTO)) {
                    if (checkIdeanticalProductsPrice(transactionTO)) {
                        return transactionTO;
                    }
                }
            }

        return transactionTO;
    }

    /*
       methods set to public to be tested
        */

    /**
     * Checking if there are less then 3 REALIZED transactions
     * - get transactions of customer that are status REALIZED
     * - check if their amount is less than 3
     *
     * @param transactionTO
     * @return true or Exception thrown
     */
    public boolean checkThreeTransactions(TransactionTO transactionTO) {
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


    /**
     * check if the order is less than 5000 zl
     *
     * @param transactionTO
     * @return true or Exception thrown
     */
    public boolean checkOrderLessThan5000(TransactionTO transactionTO) {

        Collection<Long> positionsIDs = transactionTO.getPositions();
        Float priceAll = 0.0f;
        for (Long position : positionsIDs) {
            PositionEntity positionEntity = positionDao.getOne(position);
            Float positionPriceOne = productDao.getOne(positionEntity.getId()).getPrice();
            Integer positionAmount = positionEntity.getAmount();
            Float positionPriceAll = positionPriceOne * positionAmount;
            priceAll += positionPriceAll;
        }

        if (priceAll > 5000) {
            throw new RuntimeException("You have too little realized transactions on your account to buy for over 5000zl (you selected items for " + priceAll + " )");
        }
        return true;
    }

    /**
     * Check if there are 5 identical products in transaction and what single price of them is
     * - get all products
     * - check count of each id
     * - final condition
     *
     * @param transactionTO
     * @return true or Exception thrown
     */

    public boolean checkIdeanticalProductsPrice(TransactionTO transactionTO) {
        Collection<Long> positionsIDs = transactionTO.getPositions();
        Collection<ProductEntity> productsAll = new ArrayList<>();

        for (Long position : positionsIDs) {
            PositionEntity positionEntity = positionDao.getOne(position);
            for (int i = 0; i < positionEntity.getAmount(); i++) {
                productsAll.add(positionEntity.getProduct());
            }
        }
        for (ProductEntity product : productsAll) {
            int count = 0;
            Long productId = product.getId();
            for (ProductEntity productCompare : productsAll) {
                Long productIdCompare = productCompare.getId();
                if (productId == productIdCompare) {
                    count++;
                }
            }
            if (count > 5 && product.getPrice() > 7000) {
                throw new RuntimeException("You have too little realized transactions on your account to buy identical products more than 5 which single price is over 7000 (you want to buy " + count + " identical products and single price is " + product.getPrice() + " )");
            }
        }
        return true;
    }

    /**
     * Check if wage is not over 25 kg
     * - get all the products from all position of transaction
     * - count the overall wage
     * - check condition
     * - for further extension adding price of overdelivery to your bucket price
     * @param transactionTO
     * @return true or Exception
     */

    public boolean checkWageNotOver(TransactionTO transactionTO) {
        Collection<Long> positionsIDs = transactionTO.getPositions();
        Collection<ProductEntity> productsAll = new ArrayList<>();

        for (Long position : positionsIDs) {
            PositionEntity positionEntity = positionDao.getOne(position);
            for (int i = 0; i < positionEntity.getAmount(); i++) {
                productsAll.add(positionEntity.getProduct());
            }
        }
        Integer wageAll = 0;
        for (ProductEntity product : productsAll) {
            wageAll += product.getWage();
        }
        if (wageAll > 25) {
            throw new RuntimeException("Wage too heavy (" + wageAll + ") for one delivery, 10zl will be added to your overall price");
            //send the message with overreaching the wage and add price o 10 to the end price
        }
        return true;
    }

}
