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

    private Long id;
    private String name;
    private String surname;
    private Date birth;
    private String email;
    private Integer phone;
    private String address;

    @Singular
    private Collection<Long> transactions;
}
