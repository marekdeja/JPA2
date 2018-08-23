package com.capgemini.types;


import com.capgemini.domain.TransactionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Collection;
import java.util.Date;

@Getter
@Builder
public class ProductTO {

    public int version;
    private Date createDate;
    private Date modifiedDate;

    private Long id;
    private String name;
    private Float price;
    private Float profit;
    private Integer wage;

    @Singular
    private Collection<Long> positions;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Integer getWage() {
        return wage;
    }

    public void setWage(Integer wage) {
        this.wage = wage;
    }

    public Collection<Long> getPositions() {
        return positions;
    }

    public void setPositions(Collection<Long> positions) {
        this.positions = positions;
    }
}
