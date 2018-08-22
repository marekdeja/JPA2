package com.capgemini.dao.impl;

import com.capgemini.dao.CustomerDao;
import com.capgemini.domain.CustomerEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerDaoImpl extends AbstractDao<CustomerEntity, Long> implements CustomerDao {
}
