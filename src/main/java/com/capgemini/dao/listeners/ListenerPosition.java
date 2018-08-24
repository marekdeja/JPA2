package com.capgemini.dao.listeners;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.PositionEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


public class ListenerPosition {

    @PrePersist
    public void generateCreateDate (PositionEntity c){
        c.setCreateDate(new Date());
    }
    @PreUpdate
    public void generateModifiedDate (PositionEntity c){
        c.setModifiedDate(new Date());
    }


}
