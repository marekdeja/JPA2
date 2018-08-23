package com.capgemini.dao;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionDaoSpringData extends CrudRepository<TransactionEntity, Long> {

    void removeById(Long id);
    List<TransactionEntity> findAll();
    TransactionEntity findOne(Long id);
    TransactionEntity save(TransactionEntity persisted);

    List<TransactionEntity> findByCustomerId (Long id);

}
