package com.capgemini.dao;

import com.capgemini.domain.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class SearchCriteria {
    private String customerName;
    private Date start;
    private Date end;
    private ProductEntity product;
    private Float priceTransaction;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Float getPriceTransaction() {
        return priceTransaction;
    }

    public void setPriceTransaction(Float priceTransaction) {
        this.priceTransaction = priceTransaction;
    }
}
