package com.capgemini.dao;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


public class ListenerCustomer {

    @PrePersist
    public void generateCreateDate (CustomerEntity c){
        c.setCreateDate(new Date());
    }
    @PreUpdate
    public void generateModifiedDate (CustomerEntity c){
        c.setModifiedDate(new Date());
    }


}
