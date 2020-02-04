package com.catalogue.model;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String make;
    private String model;
    private String year;
    private String price;

    public Car(){

    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getMake(){
        return make;
    }

    public void setMake(String make){
        this.make = make;
    }

    public String getModel(){
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    public String getYear(){
        return year;
    }

    public void setYear(String year){
        this.year = year;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public Car(String make, String model, String year, String price){
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    @Override
    public String toString(){
        return "Car: " + make + " | " + model + " | " + year + " | " + price + "\n";
    }
}
