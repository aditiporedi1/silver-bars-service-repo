package com.example.restful.restfulservice.doa.repository;

;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.restful.restfulservice.RestfulServiceApplication;
import com.example.restful.restfulservice.dao.repository.OrderRepository;
import com.example.restful.restfulservice.entity.Order;
import com.example.restful.restfulservice.entity.views.OrderSummaryView;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfulServiceApplication.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private static List<Order> orders;

    @Before
    public void setup() {

        orders = new ArrayList<>(Arrays.asList(new Order("user1",309.00, 1.2F, "SELL")
                ,new Order("user2",306.00, 1.3F, "SELL")
                ,new Order("user3",302.00, 1.5F, "SELL")
                ,new Order("user4",310.00, 1.8F, "BUY")
                ,new Order("user5",308.00, 1.2F, "BUY")));
        setUpOrders();
    }

    @Test
    public void shouldGetLiveOrderSummary() {

        List<OrderSummaryView> orderSummaryViews = orderRepository.getLiveOrderSummary();
        assertNotNull(orderSummaryViews);
        assertThat(orderSummaryViews.size(),is(5) );
    }

    private void setUpOrders(){
        for (Order order : orders){
            orderRepository.save(order);
        }
    }

}
