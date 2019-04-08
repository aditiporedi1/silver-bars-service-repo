package com.example.restful.restfulservice.dao.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.restful.restfulservice.dao.repository.OrderRepository;
import com.example.restful.restfulservice.data.OrderSummary;
import com.example.restful.restfulservice.entity.Order;
import com.example.restful.restfulservice.entity.views.OrderSummaryView;
import com.example.restful.restfulservice.enums.OrderType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoServiceTest {

    @InjectMocks
    private OrderDaoService orderDaoService;


    private Optional<Order> optionalOrder = Optional.of(new Order("user1", 302.00, 1.2F, "SELL"));

    @Mock
    private OrderRepository orderRepository;

   private static final List<OrderSummaryView> orderSummaryViewList = new ArrayList<>(Arrays.asList(new OrderSummaryView(309.00, 1.2, "SELL")
                                                                    ,new OrderSummaryView(306.00, 1.3, "SELL")
                                                                   ,new OrderSummaryView(302.00, 1.5, "SELL")
                                                                   ,new OrderSummaryView(310.00, 1.8, "BUY")
                                                                   ,new OrderSummaryView(308.00, 1.2, "BUY")));

    @Test
    public void shouldFindAll(){

        when(orderRepository.getLiveOrderSummary()).thenReturn(orderSummaryViewList);

        OrderSummary orderSummary = orderDaoService.findAll();

        assertThat(orderSummary.getLiveOrdersList(), hasSize(2));
        assertEquals(OrderType.SELL.getValue(), orderSummary.getLiveOrdersList().get(0).getOrderType().getValue());
        assertEquals(OrderType.BUY.getValue(), orderSummary.getLiveOrdersList().get(1).getOrderType().getValue());
        assertThat(orderSummary.getLiveOrdersList().get(0).getOrderDetails(), hasSize(3));
        assertThat(orderSummary.getLiveOrdersList().get(1).getOrderDetails(), hasSize(2));
        assertEquals(Double.valueOf("302.0"), orderSummary.getLiveOrdersList().get(0).getOrderDetails().get(0).getPricePerKg());
        assertEquals(Double.valueOf("306.0"), orderSummary.getLiveOrdersList().get(0).getOrderDetails().get(1).getPricePerKg());
        assertEquals(Double.valueOf("309.0"), orderSummary.getLiveOrdersList().get(0).getOrderDetails().get(2).getPricePerKg());
        assertEquals(Double.valueOf("310.0"), orderSummary.getLiveOrdersList().get(1).getOrderDetails().get(0).getPricePerKg());
        assertEquals(Double.valueOf("308.0"), orderSummary.getLiveOrdersList().get(1).getOrderDetails().get(1).getPricePerKg());
        assertEquals(new BigDecimal("1.20"), orderSummary.getLiveOrdersList().get(1).getOrderDetails().get(1).getQuantity());
        assertEquals(new BigDecimal("1.50"), orderSummary.getLiveOrdersList().get(0).getOrderDetails().get(0).getQuantity());
        assertEquals(new BigDecimal("1.30"), orderSummary.getLiveOrdersList().get(0).getOrderDetails().get(1).getQuantity());
        assertEquals(new BigDecimal("1.20"), orderSummary.getLiveOrdersList().get(0).getOrderDetails().get(2).getQuantity());
        assertEquals(new BigDecimal("1.80"), orderSummary.getLiveOrdersList().get(1).getOrderDetails().get(0).getQuantity());
        assertEquals(new BigDecimal("1.20"), orderSummary.getLiveOrdersList().get(1).getOrderDetails().get(1).getQuantity());


    }

    @Test
    public void shouldFindOne(){

        Order expected = new Order("user1", 302.00, 1.2F, "SELL");
        when(orderRepository.findById(anyString())).thenReturn(optionalOrder);

        Order result = orderDaoService.findOne("user1");

        assertEquals(expected.getUserId(), result.getUserId());
        assertEquals(expected.getOrderType(), result.getOrderType());
        assertEquals(expected.getPricePerKg(), result.getPricePerKg());
        assertEquals(expected.getQuantity(), result.getQuantity());
    }

    @Test
    public void shouldSave(){

        Order order = new Order("user8", 310.00, 1.2F, "BUY");
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(new Order("user8", 310.00, 1.2F, "BUY")));
        Order result = orderDaoService.save(order);

        assertEquals(order.getUserId(), result.getUserId());
        assertEquals(order.getOrderType(), result.getOrderType());
        assertEquals(order.getPricePerKg(), result.getPricePerKg());
        assertEquals(order.getQuantity(), result.getQuantity());
    }

    @Test
    public void shouldDelete(){
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(new Order("user8", 302.00, 1.2F, "SELL")));
        Boolean result = orderDaoService.deleteOrder("user8");

        assertTrue(result);
    }

}
