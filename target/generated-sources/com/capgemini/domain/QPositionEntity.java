package com.capgemini.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPositionEntity is a Querydsl query type for PositionEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPositionEntity extends EntityPathBase<PositionEntity> {

    private static final long serialVersionUID = -47771038L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPositionEntity positionEntity = new QPositionEntity("positionEntity");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final QProductEntity product;

    public final QTransactionEntity transaction;

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QPositionEntity(String variable) {
        this(PositionEntity.class, forVariable(variable), INITS);
    }

    public QPositionEntity(Path<? extends PositionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPositionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPositionEntity(PathMetadata metadata, PathInits inits) {
        this(PositionEntity.class, metadata, inits);
    }

    public QPositionEntity(Class<? extends PositionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProductEntity(forProperty("product")) : null;
        this.transaction = inits.isInitialized("transaction") ? new QTransactionEntity(forProperty("transaction"), inits.get("transaction")) : null;
    }

}

