package com.capgemini.service;

import com.capgemini.types.PositionTO;

import java.util.Collection;

public interface PositionService {

    PositionTO savePosition(PositionTO positionTO);
    void removePosition(Long id);
    Collection<PositionTO> findAll();
    PositionTO findOne(Long id);

}
