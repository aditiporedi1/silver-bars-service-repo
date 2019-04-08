package com.example.restful.restfulservice.data;

import java.util.List;

public class OrderSummary {

    List<LiveOrders> liveOrdersList;

    public OrderSummary(List<LiveOrders> liveOrdersList) {
        this.liveOrdersList = liveOrdersList;
    }

    public OrderSummary(){

    }

    public List<LiveOrders> getLiveOrdersList() {
        return liveOrdersList;
    }

    public void setLiveOrdersList(List<LiveOrders> liveOrdersList) {
        this.liveOrdersList = liveOrdersList;
    }
}
