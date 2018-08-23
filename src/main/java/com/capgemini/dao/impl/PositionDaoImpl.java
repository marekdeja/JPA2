package com.capgemini.dao.impl;

import com.capgemini.dao.PositionDao;
import com.capgemini.dao.ProductDao;
import com.capgemini.domain.PositionEntity;
import com.capgemini.domain.ProductEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PositionDaoImpl extends AbstractDao<PositionEntity, Long> implements PositionDao {
}
