package com.capgemini.mappers;

import com.capgemini.domain.TransactionEntity;
import com.capgemini.domain.PositionEntity;
import com.capgemini.types.TransactionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    CustomerMapper customerMapper;


    public static TransactionTO toTransactionTO(TransactionEntity transactionEntity) {
        if (transactionEntity == null)
            return null;

        Collection<Long> positionIDs = new HashSet<>();
        Collection<PositionEntity> positions = transactionEntity.getPositions();

        if (positions!=null) {
            for (PositionEntity element : positions) {
                positionIDs.add(element.getId());
            }
        }

        return TransactionTO.builder()
                .id(transactionEntity.getId())
                .status(transactionEntity.getStatus())
                .date(transactionEntity.getDate())
                .amount(transactionEntity.getAmount())
                .customer(CustomerMapper.toCustomerTO(transactionEntity.getCustomer()))
                .positions(positionIDs)
                .build();
    }

    public TransactionEntity toTransactionEntity(TransactionTO transactionTO) {
        if (transactionTO == null)
            return null;

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transactionTO.getId());
        transactionEntity.setStatus(transactionTO.getStatus());
        transactionEntity.setDate(transactionTO.getDate());
        transactionEntity.setAmount(transactionTO.getAmount());
        transactionEntity.setCustomer(customerMapper.toCustomerEntity(transactionTO.getCustomer()));

        Collection<Long> positions = transactionTO.getPositions();
        Collection<PositionEntity> positionEntities = new HashSet<>();

        if (positions!=null){
            for (Long element : positions){
                PositionEntity positionEntity = entityManager.getReference(PositionEntity.class, element);
                positionEntities.add(positionEntity);
            }
            transactionEntity.setPositions(positionEntities);
        }

        return transactionEntity;
    }

    public static Collection<TransactionTO> map2TOs (Collection<TransactionEntity> transactionEntities){
        if (transactionEntities==null){
            return new HashSet<>();
        }
        return transactionEntities.stream().map(TransactionMapper::toTransactionTO).collect(Collectors.toList());
    }

    public Collection<TransactionEntity> map2Entities (Collection<TransactionTO> transactionTOs){
        if (transactionTOs==null){
            return new HashSet<>();
        }
        return transactionTOs.stream().map(this::toTransactionEntity).collect(Collectors.toList());
    }
}
