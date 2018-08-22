package com.capgemini.dao.impl;

import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.TransactionDao;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TransactionDaoImpl extends AbstractDao<TransactionEntity, Long> implements TransactionDao {
}
