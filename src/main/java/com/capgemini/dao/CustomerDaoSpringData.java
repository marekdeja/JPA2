package com.capgemini.dao;

import com.capgemini.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerDaoSpringData extends JpaRepository<CustomerEntity, Long> {

    List<CustomerEntity> findByName (String name);
    List<CustomerEntity> findBySurname (String name);

    void removeById(Long id);
    List<CustomerEntity> findAll();
    CustomerEntity findOne(Long id);
    CustomerEntity save(CustomerEntity persisted);

}
