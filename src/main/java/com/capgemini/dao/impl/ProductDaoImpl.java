package com.capgemini.dao.impl;

import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.ProductDao;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.ProductEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDaoImpl extends AbstractDao<ProductEntity, Long> implements ProductDao {
}
