package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.orders.validationRules.BasketLimitPolicy;
import com.e_commerce.order_processing.orders.validationRules.FraudDetectionPolicy;
import com.e_commerce.order_processing.orders.validationRules.ItemsAvaliablityPolicy;
import com.e_commerce.order_processing.orders.validationRules.OrderPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OrderValidator {

    List<OrderPolicy> policies;


    public OrderValidator(@Autowired ItemsAvaliablityPolicy itemsAvaliablityPolicy,@Autowired FraudDetectionPolicy fraudPolicy, @Autowired BasketLimitPolicy basketLimitPolicy) {
        policies = Arrays.asList(itemsAvaliablityPolicy,basketLimitPolicy, fraudPolicy);
    }

    public void validate(Order order) {
        policies.forEach(policy -> policy.validate(order));
    }
}
