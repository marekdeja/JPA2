package com.capgemini.types;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class PositionTO {
    public int version;
    private Date createDate;
    private Date modifiedDate;
    private Long id;
    private Integer amount;
    private TransactionTO transaction;
    private ProductTO product;

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

    public TransactionTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionTO transaction) {
        this.transaction = transaction;
    }

    public ProductTO getProduct() {
        return product;
    }

    public void setProduct(ProductTO product) {
        this.product = product;
    }
}
