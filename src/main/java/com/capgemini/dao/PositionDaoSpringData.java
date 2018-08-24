package com.capgemini.dao;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.PositionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionDaoSpringData extends CrudRepository<PositionEntity, Long> {
    void removeById(Long id);
    List<PositionEntity> findAll();
    PositionEntity findOne(Long id);
    PositionEntity save(PositionEntity persisted);

}
