package com.capgemini.domain;

import com.capgemini.dao.ListenerCustomer;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@EntityListeners(ListenerCustomer.class)
@Table(name = "customer")
public class CustomerEntity {
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
    private String surname;
    @Column
    private Date birth;
    @Column
    private String email;
    @Column
    private Integer phone;
    @Column
    private String address;

    @OneToMany(cascade={CascadeType.REMOVE}, mappedBy = "customer")
    private Collection<TransactionEntity> transactions;

    public CustomerEntity(){

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
}
