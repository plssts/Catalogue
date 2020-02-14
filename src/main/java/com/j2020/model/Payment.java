package com.j2020.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

//@JsonDeserialize(as = Payment.class)
public interface Payment {
    Float getAmount();
    void setIdentifyingInformation(String info);
}
