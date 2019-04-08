package com.example.restful.restfulservice.entity.views;

public class OrderSummaryView {

    private Double pricePerKg;
    private Double quantity;
    private String orderType;

    public OrderSummaryView(Double pricePerKg, Double quantity, String orderType) {
        this.pricePerKg = pricePerKg;
        this.quantity = quantity;
        this.orderType = orderType;
    }


    public Double getPricePerKg() {
        return pricePerKg;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getOrderType() {
        return orderType;
    }
}
