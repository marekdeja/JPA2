package com.capgemini.dao.listeners;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;

import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


public class ListenerTransaction {

    @PrePersist
    public void generateCreateDate (TransactionEntity c){
        c.setCreateDate(new Date());
    }
    @PreUpdate
    public void generateModifiedDate (TransactionEntity c){
        c.setModifiedDate(new Date());
    }

}
