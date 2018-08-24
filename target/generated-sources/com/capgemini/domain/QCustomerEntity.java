package com.capgemini.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerEntity is a Querydsl query type for CustomerEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerEntity extends EntityPathBase<CustomerEntity> {

    private static final long serialVersionUID = -767992489L;

    public static final QCustomerEntity customerEntity = new QCustomerEntity("customerEntity");

    public final StringPath address = createString("address");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> phone = createNumber("phone", Integer.class);

    public final StringPath surname = createString("surname");

    public final CollectionPath<TransactionEntity, QTransactionEntity> transactions = this.<TransactionEntity, QTransactionEntity>createCollection("transactions", TransactionEntity.class, QTransactionEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QCustomerEntity(String variable) {
        super(CustomerEntity.class, forVariable(variable));
    }

    public QCustomerEntity(Path<? extends CustomerEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerEntity(PathMetadata metadata) {
        super(CustomerEntity.class, metadata);
    }

}

