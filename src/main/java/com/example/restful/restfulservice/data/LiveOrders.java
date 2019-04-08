package com.example.restful.restfulservice.data;

import java.util.List;

import com.example.restful.restfulservice.enums.OrderType;

public class LiveOrders {

    OrderType orderType;
    List<OrderDetails> orderDetails;


    public LiveOrders(OrderType orderType, List<OrderDetails> orderDetails) {
        this.orderType = orderType;
        this.orderDetails = orderDetails;
    }

    public LiveOrders(){

    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
