package com.capgemini.mappers;

import com.capgemini.dao.TransactionDao;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.CustomerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    @PersistenceContext
    private EntityManager entityManager;

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

    public CustomerEntity toCustomerEntity(CustomerTO customerTO) {
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
                TransactionEntity transactionEntity = entityManager.getReference(TransactionEntity.class, element);
                transactionEntities.add(transactionEntity);
            }
            customerEntity.setTransactions(transactionEntities);

        }


        return customerEntity;

    }

    public static Collection<CustomerTO> map2TOs (Collection<CustomerEntity> customerEntities){
        if (customerEntities==null){
            return new HashSet<>();
        }
        return customerEntities.stream().map(CustomerMapper::toCustomerTO).collect(Collectors.toList());
    }

    public Collection<CustomerEntity> map2Entities (Collection<CustomerTO> customerTOs){
        if (customerTOs==null){
            return new HashSet<>();
        }
        return customerTOs.stream().map(this::toCustomerEntity).collect(Collectors.toList());
    }
}
