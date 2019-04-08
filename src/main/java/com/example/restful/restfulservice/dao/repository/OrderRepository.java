package com.example.restful.restfulservice.dao.repository;

import java.util.List;

import com.example.restful.restfulservice.entity.Order;
import com.example.restful.restfulservice.entity.views.OrderSummaryView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {

    @Query("SELECT new com.example.restful.restfulservice.entity.views.OrderSummaryView(o.pricePerKg, sum(o.quantity), o.orderType) FROM Order o Group By o.pricePerKg, o.orderType")
    List<OrderSummaryView> getLiveOrderSummary();
}
