package com.capgemini.service.impl;

import com.capgemini.dao.*;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.PositionEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.Status;
import com.capgemini.mappers.CustomerMapper;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.CustomerService;
import com.capgemini.service.TransactionService;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerDaoSpringData customerDaoSpringData;

    @Override
    public CustomerTO saveCustomer(CustomerTO customerTO) {
        CustomerEntity customerEntity = customerDao.save(customerMapper.toCustomerEntity(customerTO));
        return CustomerMapper.toCustomerTO((customerEntity));
    }

    @Override
    public void removeCustomer(Long id) {
        customerDaoSpringData.removeById(id);
    }

    @Override
    public Collection<CustomerTO> findAll() {
        Collection<CustomerEntity> customerFound = customerDaoSpringData.findAll();
        return CustomerMapper.map2TOs(customerFound);
    }

    @Override
    public CustomerTO findOne(Long id) {
        return CustomerMapper.toCustomerTO(customerDaoSpringData.findOne(id));
    }


}
