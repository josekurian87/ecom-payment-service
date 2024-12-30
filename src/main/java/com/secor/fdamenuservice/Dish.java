package com.secor.fdamenuservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @Column(name = "dishid", nullable = false, length = 50)
    private String dishid;

    @Column(name = "dishname", length = 50)
    private String dishname;

    @Column(name = "restroid", length = 50)
    private String restroid;

    @Column(name = "price")
    private Integer price;

    public String getDishid() {
        return dishid;
    }

    public void setDishid(String dishid) {
        this.dishid = dishid;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getRestroid() {
        return restroid;
    }

    public void setRestroid(String restroid) {
        this.restroid = restroid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}