package com.example.restful.restfulservice.ittests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.restful.restfulservice.RestfulServiceApplication;
import com.example.restful.restfulservice.dao.service.OrderDaoService;
import com.example.restful.restfulservice.data.LiveOrders;
import com.example.restful.restfulservice.data.OrderDetails;
import com.example.restful.restfulservice.data.OrderSummary;
import com.example.restful.restfulservice.entity.Order;
import com.example.restful.restfulservice.entity.views.OrderSummaryView;
import com.example.restful.restfulservice.enums.OrderType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfulServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class OrderControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDaoService service;

    final List<OrderDetails> orderDetailsSell = new ArrayList<>(Arrays.asList(new OrderDetails(new BigDecimal(1.20).setScale(2,RoundingMode.HALF_UP), 210.00), new OrderDetails(new BigDecimal(1.50).setScale(2,RoundingMode.HALF_UP), 250.00), new OrderDetails(new BigDecimal(2.20).setScale(2,RoundingMode.HALF_UP), 260.00), new OrderDetails(new BigDecimal(3.20).setScale(2,RoundingMode.HALF_UP), 270.00)));

    final List<OrderDetails> orderDetailsBuy = new ArrayList<>(Arrays.asList(new OrderDetails(new BigDecimal(1.20).setScale(2,RoundingMode.HALF_UP), 270.00), new OrderDetails(new BigDecimal(1.50).setScale(2,RoundingMode.HALF_UP), 260.00), new OrderDetails(new BigDecimal(2.20).setScale(2,RoundingMode.HALF_UP), 250.00), new OrderDetails(new BigDecimal(3.20).setScale(2,RoundingMode.HALF_UP), 210.00)));

    final List<LiveOrders> liveOrders = new ArrayList<>(Arrays.asList(new LiveOrders(OrderType.SELL, orderDetailsSell), new LiveOrders(OrderType.BUY, orderDetailsBuy)));

    final OrderSummary orderSummary = new OrderSummary(liveOrders);


    @Test
    public void getOrderSummary_forLiveOrders_thenReturnJsonArray()
            throws Exception {

        given(service.findAll()).willReturn(orderSummary);

        mockMvc.perform(get("/get-order-summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.liveOrdersList[0].orderType", is("SELL")))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[0].quantity", is(1.2)))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[1].quantity", is(1.5)))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[2].quantity", is(2.2)))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[3].quantity", is(3.2)))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[0].pricePerKg", is(210.00)))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[1].pricePerKg", is(250.00)))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[2].pricePerKg", is(260.00)))
                .andExpect(jsonPath("$.liveOrdersList[0].orderDetails[3].pricePerKg", is(270.00)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderType", is("BUY")))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[0].quantity", is(1.2)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[1].quantity", is(1.5)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[2].quantity", is(2.2)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[3].quantity", is(3.2)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[0].pricePerKg", is(270.00)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[1].pricePerKg", is(260.00)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[2].pricePerKg", is(250.00)))
                .andExpect(jsonPath("$.liveOrdersList[1].orderDetails[3].pricePerKg", is(210.00)));
    }

}
