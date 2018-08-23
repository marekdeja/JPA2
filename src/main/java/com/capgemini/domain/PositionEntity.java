package com.capgemini.domain;

import com.capgemini.dao.listeners.ListenerCustomer;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(ListenerCustomer.class)
@Table(name = "position")
public class PositionEntity {
    private static final long serialVersionUID = 1L;

    @Version
    public int version;
    @Column
    private Date createDate;
    @Column
    private Date modifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Integer amount;

    @ManyToOne
    private TransactionEntity transaction;

    @ManyToOne
    private ProductEntity product;

    public PositionEntity() {
    }
    public PositionEntity(Integer amount, TransactionEntity transaction, ProductEntity product) {
        this.amount = amount;
        this.transaction = transaction;
        this.product = product;
    }


    public PositionEntity(Long id, Integer amount, TransactionEntity transaction, ProductEntity product) {
        this.id = id;
        this.amount = amount;
        this.transaction = transaction;
        this.product = product;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transaction) {
        this.transaction = transaction;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
