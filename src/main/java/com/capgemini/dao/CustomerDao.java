package com.capgemini.dao;

import com.capgemini.domain.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerDao extends Dao<CustomerEntity, Long> {

}
