package com.capgemini.types;


import com.capgemini.domain.TransactionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;


import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Getter
@Builder
public class CustomerTO {

    public int version;
    private Date createDate;
    private Date modifiedDate;

    private Long id;
    private String name;
    private String surname;
    private Date birth;
    private String email;
    private Integer phone;
    private String address;

    @Singular
    private Collection<Long> transactions;

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

    public Collection<Long> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Long> transactions) {
        this.transactions = transactions;
    }
}
