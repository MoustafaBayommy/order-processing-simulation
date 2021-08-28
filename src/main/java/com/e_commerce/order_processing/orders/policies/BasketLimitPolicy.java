package com.e_commerce.order_processing.orders.policies;

import com.e_commerce.order_processing.orders.Order;
import com.e_commerce.order_processing.orders.OrderService;
import com.e_commerce.order_processing.util.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BasketLimitPolicy implements OrderPolicy {

    @Autowired
    OrderService orderService;

    @Override
    public void validate(Order order) throws HttpException {
        float basketTotal = orderService.getBasketTotal(order);
        if (basketTotal < 100) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Basket value should be more than 100");
        }
    }
}
