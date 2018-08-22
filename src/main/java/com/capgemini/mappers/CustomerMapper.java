package com.capgemini.mappers;

import com.capgemini.dao.TransactionDao;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.CustomerTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashSet;


public class CustomerMapper {
    @Autowired
    private static TransactionDao transactionDao;

    public static CustomerTO toCustomerTO(CustomerEntity customerEntity) {
        if (customerEntity == null)
            return null;

        Collection<Long> transactionsIDs = new HashSet<>();
        Collection<TransactionEntity> transactions = customerEntity.getTransactions();

        if (transactions!=null) {
            for (TransactionEntity element : transactions) {
                transactionsIDs.add(element.getId());
            }
        }

        return CustomerTO.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .surname(customerEntity.getSurname())
                .birth(customerEntity.getBirth())
                .email(customerEntity.getEmail())
                .phone(customerEntity.getPhone())
                .address(customerEntity.getAddress())
                .transactions(transactionsIDs)
                .build();
    }

    public static CustomerEntity toCustomerEntity(CustomerTO customerTO) {
        if (customerTO == null)
            return null;

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerTO.getId());
        customerEntity.setName(customerTO.getName());
        customerEntity.setSurname(customerTO.getSurname());
        customerEntity.setBirth(customerTO.getBirth());
        customerEntity.setEmail(customerTO.getEmail());
        customerEntity.setPhone(customerTO.getPhone());
        customerEntity.setAddress(customerTO.getAddress());

        Collection<Long> transactions = customerTO.getTransactions();
        Collection<TransactionEntity> transactionEntities = new HashSet<>();

        if (transactions!=null){
            for (Long element : transactions){
                TransactionEntity transactionEntity = transactionDao.getOne(element);
                transactionEntities.add(transactionEntity);
            }
            customerEntity.setTransactions(transactionEntities);

        }


        return customerEntity;

    }
}
