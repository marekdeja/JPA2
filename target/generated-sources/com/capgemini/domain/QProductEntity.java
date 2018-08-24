package com.capgemini.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = -267932932L;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final StringPath name = createString("name");

    public final CollectionPath<PositionEntity, QPositionEntity> positions = this.<PositionEntity, QPositionEntity>createCollection("positions", PositionEntity.class, QPositionEntity.class, PathInits.DIRECT2);

    public final NumberPath<Float> price = createNumber("price", Float.class);

    public final NumberPath<Float> profit = createNumber("profit", Float.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public final NumberPath<Integer> wage = createNumber("wage", Integer.class);

    public QProductEntity(String variable) {
        super(ProductEntity.class, forVariable(variable));
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductEntity(PathMetadata metadata) {
        super(ProductEntity.class, metadata);
    }

}

