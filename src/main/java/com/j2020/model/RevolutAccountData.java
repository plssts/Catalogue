package com.j2020.model;

import lombok.Data;

@Data
public class RevolutAccountData {
    private String id;
    private String name;
    private Float balance;
    private String currency;
    private String state;
    private Boolean isPublic;
    private String created_at;
    private String updated_at;

    // Public setter since Jackson expects the field 'public' which is a Java keyword
    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
