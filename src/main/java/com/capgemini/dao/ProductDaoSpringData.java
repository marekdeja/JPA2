package com.capgemini.dao;

import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDaoSpringData extends CrudRepository<ProductEntity, Long> {

    void removeById(Long id);
    List<ProductEntity> findAll();
    ProductEntity findOne(Long id);
    ProductEntity save(ProductEntity persisted);


}
