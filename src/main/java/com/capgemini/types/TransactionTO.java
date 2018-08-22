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

    private Long id;
    private Status status;
    private Date date;
    private Integer amount;

    private CustomerTO customer;

    @Singular
    private Collection<Long> productIDs;

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
