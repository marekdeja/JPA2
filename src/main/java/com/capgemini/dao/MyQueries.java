package com.capgemini.dao;

import com.capgemini.domain.CustomerEntity;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class MyQueries {

    @Autowired
    EntityManager entityManager;

    public List<CustomerEntity> getCustomers(String name) {
        JPAQuery query = new JPAQuery(entityManager);

        QCustomer customer = QCustomer.customer;
        return query.from(customer).where(customer.name.eq(name)).list(customer);
    }


}

