/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.sun.javafx.beans.IDProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RevolutTransactionMerchant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String city;

    @JsonProperty(value = "category_code")
    private String categoryCode;

    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
