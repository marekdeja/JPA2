package com.capgemini.domain;

import com.capgemini.dao.listeners.ListenerProduct;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@EntityListeners(ListenerProduct.class)
@Table(name = "product")
public class ProductEntity {

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
    private String name;
    @Column
    private Float price;
    @Column
    private Float profit;
    @Column
    private Integer wage;

    @OneToMany(cascade={CascadeType.REMOVE}, mappedBy = "product")
    private Collection<PositionEntity> positions;

    public ProductEntity() {
    }

    public ProductEntity(String name, Float price, Float profit, Integer wage, Collection<PositionEntity> positions) {
        this.name = name;
        this.price = price;
        this.profit = profit;
        this.wage = wage;
        this.positions = positions;
    }

    public ProductEntity(Long id, String name, Float price, Float profit, Integer wage, Collection<PositionEntity> positions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.profit = profit;
        this.wage = wage;
        this.positions = positions;
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

    public Collection<PositionEntity> getPositions() {
        return positions;
    }

    public void setPositions(Collection<PositionEntity> positions) {
        this.positions = positions;
    }
}
