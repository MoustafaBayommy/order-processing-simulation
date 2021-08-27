package com.e_commerce.order_processing.orders.validationRules;

import com.e_commerce.order_processing.orders.Order;
import com.e_commerce.order_processing.orders.OrderService;
import com.e_commerce.order_processing.util.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FraudDetectionPolicy implements OrderPolicy {

    @Autowired
    private OrderService orderService;

    @Override
    public void validate(Order order) throws HttpException {
        float basketTotal=orderService.getBasketTotal(order);
        if(basketTotal>1500){
            throw new HttpException(HttpStatus.FORBIDDEN, " fraud detection");
        }
    }
}
