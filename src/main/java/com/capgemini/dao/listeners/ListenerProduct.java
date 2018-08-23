package com.capgemini.dao.listeners;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.ProductEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


public class ListenerProduct {

    @PrePersist
    public void generateCreateDate (ProductEntity c){
        c.setCreateDate(new Date());
    }
    @PreUpdate
    public void generateModifiedDate (ProductEntity c){
        c.setModifiedDate(new Date());
    }

}
