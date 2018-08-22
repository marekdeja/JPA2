package com.capgemini.mappers;

import com.capgemini.dao.TransactionDao;
import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.ProductTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;

public class ProductMapper {
    @Autowired
    private static TransactionDao transactionDao;

    public static ProductTO toProductTO(ProductEntity productEntity) {
        if (productEntity == null)
            return null;

        Collection<Long> transactionsIDs = new HashSet<>();
        Collection<TransactionEntity> transactions = productEntity.getTransactions();

        if (transactions!=null) {
            for (TransactionEntity element : transactions) {
                transactionsIDs.add(element.getId());
            }
        }

        return ProductTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .profit(productEntity.getProfit())
                .wage(productEntity.getWage())
                .transactions(transactionsIDs)
                .build();
    }

    public static ProductEntity toProductEntity(ProductTO productTO) {
        if (productTO == null)
            return null;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productTO.getId());
        productEntity.setName(productTO.getName());
        productEntity.setPrice(productTO.getPrice());
        productEntity.setProfit(productTO.getProfit());
        productEntity.setWage(productTO.getWage());

        Collection<Long> transactions = productTO.getTransactions();
        Collection<TransactionEntity> transactionEntities = new HashSet<>();

        if (transactions!=null){
            for (Long element : transactions){
                TransactionEntity transactionEntity = transactionDao.getOne(element);
                transactionEntities.add(transactionEntity);
            }
            productEntity.setTransactions(transactionEntities);
        }


        return productEntity;

    }
}
