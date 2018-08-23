package com.capgemini.types;


import com.capgemini.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Collection;
import java.util.Date;

@Getter
@Builder
public class TransactionTO {

    public int version;
    private Date createDate;
    private Date modifiedDate;

    private Long id;
    private Status status;
    private Date date;
    private Integer amount;

    private CustomerTO customer;

    @Singular
    private Collection<Long> productIDs;

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

    public CustomerTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerTO customer) {
        this.customer = customer;
    }

    public Collection<Long> getProductIDs() {
        return productIDs;
    }

    public void setProductIDs(Collection<Long> productIDs) {
        this.productIDs = productIDs;
    }
}
