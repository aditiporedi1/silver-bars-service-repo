package com.example.restful.restfulservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS")
public class Order {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "price_per_kg")
    private Double pricePerKg;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "order_type")
    private String orderType;


    public Order(String userId, Double pricePerKg, Float quanitity, String orderType) {
        this.userId = userId;
        this.pricePerKg = pricePerKg;
        this.quantity = quanitity;
        this.orderType = orderType;
    }

    public Order(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(Double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
