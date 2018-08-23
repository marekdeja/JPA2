package com.capgemini.mappers;

import com.capgemini.domain.TransactionEntity;
import com.capgemini.domain.ProductEntity;
import com.capgemini.types.TransactionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;

@Component
public class TransactionMapper {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    CustomerMapper customerMapper;

    public static TransactionTO toTransactionTO(TransactionEntity transactionEntity) {
        if (transactionEntity == null)
            return null;

        Collection<Long> productIDs = new HashSet<>();
        Collection<ProductEntity> products = transactionEntity.getProductEntities();

        if (products!=null) {
            for (ProductEntity element : products) {
                productIDs.add(element.getId());
            }
        }

        return TransactionTO.builder()
                .id(transactionEntity.getId())
                .status(transactionEntity.getStatus())
                .date(transactionEntity.getDate())
                .amount(transactionEntity.getAmount())
                .customer(CustomerMapper.toCustomerTO(transactionEntity.getCustomer()))
                .productIDs(productIDs)
                .build();
    }

    public TransactionEntity toTransactionEntity(TransactionTO transactionTO) {
        if (transactionTO == null)
            return null;

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transactionTO.getId());
        transactionEntity.setStatus(transactionTO.getStatus());
        transactionEntity.setDate(transactionTO.getDate());
        transactionEntity.setAmount(transactionTO.getAmount());
        transactionEntity.setCustomer(customerMapper.toCustomerEntity(transactionTO.getCustomer()));

        Collection<Long> products = transactionTO.getProductIDs();
        Collection<ProductEntity> productEntities = new HashSet<>();

        if (products!=null){
            for (Long element : products){
                ProductEntity productEntity = entityManager.getReference(ProductEntity.class, element);
                productEntities.add(productEntity);
            }
            transactionEntity.setProductEntities(productEntities);
        }



        return transactionEntity;

    }
}
