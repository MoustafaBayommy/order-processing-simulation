package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.customers.Customer;
import com.e_commerce.order_processing.orders.policies.BasketLimitPolicy;
import com.e_commerce.order_processing.orders.policies.FraudCustomerPolicy;
import com.e_commerce.order_processing.util.HttpException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class OderFraudDetectionPolicies {


    @Autowired
    OrderValidator orderValidator;

    @MockBean
    OrderService orderService;
    @Autowired
    BasketLimitPolicy basketLimitPolicy;
    @Autowired
    FraudCustomerPolicy fraudDetectionPolicy;


    @Test(expected = HttpException.class)
    public void testViolatesBasketMinlimit() {
        Order order = new Order();
        order.setId("orderId");
        order.setCustomer(new Customer());
        orderValidator.setPolicies(Arrays.asList(basketLimitPolicy));

        orderValidator.validate(order);
    }


    @Test(expected = HttpException.class)
    public void testViolatesFraudCustomer() {

        Mockito.when(orderService.getBasketTotal(Matchers.any()))
                .thenReturn(2222f);
        Order order = new Order();
        order.setId("orderId");
        order.setCustomer(new Customer());
        orderValidator.setPolicies(Arrays.asList(basketLimitPolicy, fraudDetectionPolicy));

        orderValidator.validate(order);
    }


    @Test()
    public void testValidSucessfulOrder() {

        Mockito.when(orderService.getBasketTotal(Matchers.any()))
                .thenReturn(500f);
        Order order = new Order();
        order.setId("orderId");
        order.setCustomer(new Customer());
        orderValidator.setPolicies(Arrays.asList(basketLimitPolicy, fraudDetectionPolicy));

        orderValidator.validate(order);
    }
}
