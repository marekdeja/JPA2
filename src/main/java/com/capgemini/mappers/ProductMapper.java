package com.capgemini.mappers;

import com.capgemini.dao.TransactionDao;
import com.capgemini.domain.PositionEntity;
import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.ProductTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @PersistenceContext
    private EntityManager entityManager;

    public static ProductTO toProductTO(ProductEntity productEntity) {
        if (productEntity == null)
            return null;

        Collection<Long> positionsIDs = new HashSet<>();
        Collection<PositionEntity> positions = productEntity.getPositions();

        if (positions!=null) {
            for (PositionEntity element : positions) {
                positionsIDs.add(element.getId());
            }
        }

        return ProductTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .profit(productEntity.getProfit())
                .wage(productEntity.getWage())
                .positions(positionsIDs)
                .build();
    }

    public ProductEntity toProductEntity(ProductTO productTO) {
        if (productTO == null)
            return null;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productTO.getId());
        productEntity.setName(productTO.getName());
        productEntity.setPrice(productTO.getPrice());
        productEntity.setProfit(productTO.getProfit());
        productEntity.setWage(productTO.getWage());

        Collection<Long> positions = productTO.getPositions();
        Collection<PositionEntity> positionEntities = new HashSet<>();

        if (positions!=null){
            for (Long element : positions){
                PositionEntity positionEntity = entityManager.getReference(PositionEntity.class, element);
                positionEntities.add(positionEntity);
            }
            productEntity.setPositions(positionEntities);
        }


        return productEntity;

    }


    public static Collection<ProductTO> map2TOs (Collection<ProductEntity> productEntities){
        if (productEntities==null){
            return new HashSet<>();
        }
        return productEntities.stream().map(ProductMapper::toProductTO).collect(Collectors.toList());
    }

    public Collection<ProductEntity> map2Entities (Collection<ProductTO> productTOs){
        if (productTOs==null){
            return new HashSet<>();
        }
        return productTOs.stream().map(this::toProductEntity).collect(Collectors.toList());
    }
}
