package com.capgemini.service.impl;

import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.CustomerDaoSpringData;
import com.capgemini.dao.PositionDao;
import com.capgemini.dao.PositionDaoSpringData;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.PositionEntity;
import com.capgemini.mappers.CustomerMapper;
import com.capgemini.mappers.PositionMapper;
import com.capgemini.service.CustomerService;
import com.capgemini.service.PositionService;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.PositionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionDao positionDao;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private PositionDaoSpringData positionDaoSpringData;

    @Override
    public PositionTO savePosition(PositionTO positionTO) {
        PositionEntity positionEntity = positionDao.save(positionMapper.toPositionEntity(positionTO));
        return PositionMapper.toPositionTO((positionEntity));
    }

    @Override
    public void removePosition(Long id) {
        positionDaoSpringData.removeById(id);
    }

    @Override
    public Collection<PositionTO> findAll() {
        Collection<PositionEntity> positionFound = positionDaoSpringData.findAll();
        return PositionMapper.map2TOs(positionFound);
    }

    @Override
    public PositionTO findOne(Long id) {
        return PositionMapper.toPositionTO(positionDaoSpringData.findOne(id));
    }


}
