package com.example.restful.restfulservice.data;

import java.math.BigDecimal;

public class OrderDetails {

    BigDecimal quantity;
    Double pricePerKg;

    public OrderDetails(BigDecimal quantity, Double pricePerKg) {
        this.quantity = quantity;
        this.pricePerKg = pricePerKg;
    }

    public OrderDetails(){

    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(Double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }
}
