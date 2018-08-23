package com.capgemini.domain;


import com.capgemini.dao.listeners.ListenerTransaction;
import com.capgemini.enums.Status;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@EntityListeners(ListenerTransaction.class)
@Table(name = "transaction")
public class TransactionEntity {

    private static final long serialVersionUID = 1L;

    @Version public int version;
    @Column
    private Date createDate;
    @Column
    private Date modifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Status status;
    @Column
    private Date date;
    @Column
    private Integer amount;

    @ManyToOne
    private CustomerEntity customer;

    @ManyToMany(cascade={CascadeType.REMOVE}, mappedBy = "transactions")
    private Collection<ProductEntity> productEntities;

    public TransactionEntity() {

    }

    public TransactionEntity(Status status, Date date, Integer amount, CustomerEntity customer, Collection<ProductEntity> productEntities) {
        this.status = status;
        this.date = date;
        this.amount = amount;
        this.customer = customer;
        this.productEntities = productEntities;
    }

    public TransactionEntity(Long id, Status status, Date date, Integer amount, CustomerEntity customer, Collection<ProductEntity> productEntities) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.amount = amount;
        this.customer = customer;
        this.productEntities = productEntities;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Collection<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(Collection<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }
}
