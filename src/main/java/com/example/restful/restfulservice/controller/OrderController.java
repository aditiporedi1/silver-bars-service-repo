package com.example.restful.restfulservice.controller;


import java.net.URI;
import java.util.Random;

import com.example.restful.restfulservice.dao.service.OrderDaoService;
import com.example.restful.restfulservice.data.OrderSummary;
import com.example.restful.restfulservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class OrderController {

    @Autowired
    private OrderDaoService orderDaoService;


    //GET Live Orders
    @GetMapping(path = "/get-order-summary")
    public OrderSummary getOrderSummary(){
       return orderDaoService.findAll();
    }


    @GetMapping("/order/{id}")
    public Order getUserById(@PathVariable(name = "id") final String id){
        return orderDaoService.findOne(id);
    }

    @DeleteMapping("/delete-order/{id}")
    public boolean deleteOrderById(@PathVariable(name = "id") final String id){
        return orderDaoService.deleteOrder(id);
    }

    @PostMapping("/register-order")
    public ResponseEntity<Object> registerOrder(@RequestBody Order order){

        Order savedOrder = orderDaoService.save(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(savedOrder.getUserId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
