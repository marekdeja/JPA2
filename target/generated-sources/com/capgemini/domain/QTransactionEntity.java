package com.capgemini.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactionEntity is a Querydsl query type for TransactionEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTransactionEntity extends EntityPathBase<TransactionEntity> {

    private static final long serialVersionUID = -27226965L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactionEntity transactionEntity = new QTransactionEntity("transactionEntity");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final QCustomerEntity customer;

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final CollectionPath<PositionEntity, QPositionEntity> positions = this.<PositionEntity, QPositionEntity>createCollection("positions", PositionEntity.class, QPositionEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.capgemini.enums.Status> status = createEnum("status", com.capgemini.enums.Status.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QTransactionEntity(String variable) {
        this(TransactionEntity.class, forVariable(variable), INITS);
    }

    public QTransactionEntity(Path<? extends TransactionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactionEntity(PathMetadata metadata, PathInits inits) {
        this(TransactionEntity.class, metadata, inits);
    }

    public QTransactionEntity(Class<? extends TransactionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomerEntity(forProperty("customer")) : null;
    }

}

