package com.capgemini.service;

import com.capgemini.types.CustomerTO;

import java.util.Collection;

public interface CustomerService {

    CustomerTO saveCustomer(CustomerTO customerTO);
    void removeCustomer(Long id);
    Collection<CustomerTO> findAll();
    CustomerTO findOne(Long id);

}
