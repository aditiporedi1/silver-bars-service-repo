package com.example.restful.restfulservice.data.converter;

import static java.util.Comparator.comparing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.restful.restfulservice.data.LiveOrders;
import com.example.restful.restfulservice.data.OrderDetails;
import com.example.restful.restfulservice.data.OrderSummary;
import com.example.restful.restfulservice.entity.views.OrderSummaryView;
import com.example.restful.restfulservice.enums.OrderType;

public class OrderToOrderSummaryConverter {

    public static OrderSummary convertToOrderSummary(final List<OrderSummaryView> orderSummaryViews){

        final Map<String,List<OrderDetails>> ordersMap = convertToMap(orderSummaryViews);
        final List<LiveOrders> liveOrders = new ArrayList<>();

        ordersMap.entrySet().forEach( ordersMapKey ->{
                    liveOrders.add(new LiveOrders(OrderType.valueOf(ordersMapKey.getKey()),ordersMapKey.getValue()));
                }
        );

        return new OrderSummary(liveOrders);

    }

    public static Map<String,List<OrderDetails>> convertToMap(final List<OrderSummaryView> orderSummaryViews){

        final Map<String,List<OrderDetails>> orderTypeDetailsMap = new HashMap<>();
        final Map<String, List<OrderDetails>> reverseSortedMap = new LinkedHashMap<>();

        orderSummaryViews.stream().forEach( order -> {
                    orderTypeDetailsMap.put(order.getOrderType(), new ArrayList<>());
                }
        );

        orderTypeDetailsMap.entrySet().forEach(orderType -> {
            final List<OrderDetails> orderDetailsList = new ArrayList<>();

            orderSummaryViews.forEach(order -> {
                if (order.getOrderType().equals(orderType.getKey()))
                    orderDetailsList.add(new OrderDetails(new BigDecimal(order.getQuantity()).setScale(2,RoundingMode.HALF_UP), order.getPricePerKg()));
            });
            if (OrderType.SELL.getValue().equals(orderType.getKey()))
                orderType.setValue(orderDetailsList.stream()
                        .sorted(comparing(OrderDetails::getPricePerKg))
                        .collect(Collectors.toList()));
            else if (OrderType.BUY.getValue().equals(orderType.getKey()))
                orderType.setValue(orderDetailsList.stream()
                        .sorted(comparing(OrderDetails::getPricePerKg).reversed())
                        .collect(Collectors.toList()));

        });


        orderTypeDetailsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        return reverseSortedMap;
    }

}
