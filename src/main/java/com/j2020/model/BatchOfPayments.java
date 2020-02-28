package com.j2020.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
