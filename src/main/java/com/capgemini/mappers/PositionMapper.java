package com.capgemini.mappers;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.PositionEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.PositionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class PositionMapper {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private TransactionMapper transactionMapper;

    public static PositionTO toPositionTO(PositionEntity positionEntity) {
        if (positionEntity == null)
            return null;

        return PositionTO.builder()
                .id(positionEntity.getId())
                .amount(positionEntity.getAmount())
                .transaction(TransactionMapper.toTransactionTO(positionEntity.getTransaction()))
                .product(ProductMapper.toProductTO(positionEntity.getProduct()))
                .build();
    }

    public PositionEntity toPositionEntity(PositionTO positionTO) {
        if (positionTO == null)
            return null;

        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setId(positionTO.getId());
        positionEntity.setAmount(positionTO.getAmount());
        positionEntity.setTransaction(transactionMapper.toTransactionEntity(positionTO.getTransaction()));
        positionEntity.setProduct(productMapper.toProductEntity(positionTO.getProduct()));

        return positionEntity;

    }

    public static Collection<PositionTO> map2TOs (Collection<PositionEntity> positionEntities){
        if (positionEntities==null){
            return new HashSet<>();
        }
        return positionEntities.stream().map(PositionMapper::toPositionTO).collect(Collectors.toList());
    }

    public Collection<PositionEntity> map2Entities (Collection<PositionTO> positionTOs){
        if (positionTOs==null){
            return new HashSet<>();
        }
        return positionTOs.stream().map(this::toPositionEntity).collect(Collectors.toList());
    }
}
