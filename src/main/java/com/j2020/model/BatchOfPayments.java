package com.j2020.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "bop")
public class BatchOfPayments {
    @Id
    @GeneratedValue
    private Long id;

    private Integer countOfAllPayments;
    private Integer countOfProcessedPayments;

    public Integer getCountOfAllPayments() {
        return countOfAllPayments;
    }

    public void setCountOfAllPayments(Integer countOfAllPayments) {
        this.countOfAllPayments = countOfAllPayments;
    }

    public Integer getCountOfProcessedPayments() {
        return countOfProcessedPayments;
    }

    public void setCountOfProcessedPayments(Integer countOfProcessedPayments) {
        this.countOfProcessedPayments = countOfProcessedPayments;
    }

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
