package com.example.restful.restfulservice.dao.service;

import static com.example.restful.restfulservice.data.converter.OrderToOrderSummaryConverter.convertToOrderSummary;

import java.util.ArrayList;
import java.util.List;

import com.example.restful.restfulservice.dao.repository.OrderRepository;
import com.example.restful.restfulservice.data.OrderSummary;
import com.example.restful.restfulservice.entity.Order;
import com.example.restful.restfulservice.entity.views.OrderSummaryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoService {

    @Autowired
    private OrderRepository orderRepository;


    public OrderSummary findAll(){

        List<OrderSummaryView> orderSummaryViews = orderRepository.getLiveOrderSummary();
        return convertToOrderSummary(orderSummaryViews);
    }

    public Order findOne(final String userId){

        if (orderRepository.findById(userId).isPresent())
            return orderRepository.findById(userId).get();

        return null;
    }

    public boolean deleteOrder(final String userId){

       Order order = orderRepository.findById(userId).get();
       if (order != null){
           orderRepository.deleteById(userId);
           return true;
       }
       return false;
    }

    public  Order save(Order order){
        orderRepository.save(order);
        Order newOrder = orderRepository.findById(order.getUserId()).get();
        return newOrder;
    }
}
