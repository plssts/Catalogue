package com.j2020.model;

import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import java.util.List;

@Entity(name = "bop")
public class BatchOfPayments {
    @Id
    @GeneratedValue
    private Long id;

    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TransactionStatusCheck> payments;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public List<TransactionStatusCheck> getPayments() {
        return payments;
    }

    public void setPayments(List<TransactionStatusCheck> payments) {
        this.payments = payments;
    }*/
}
