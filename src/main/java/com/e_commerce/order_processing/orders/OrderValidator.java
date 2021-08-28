package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.orders.policies.BasketLimitPolicy;
import com.e_commerce.order_processing.orders.policies.FraudCustomerPolicy;
import com.e_commerce.order_processing.orders.policies.OrderPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OrderValidator {

    List<OrderPolicy> policies;



    public OrderValidator(@Autowired FraudCustomerPolicy fraudPolicy, @Autowired BasketLimitPolicy basketLimitPolicy) {
        policies = Arrays.asList(basketLimitPolicy, fraudPolicy);
    }

    public List<OrderPolicy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<OrderPolicy> policies) {
        this.policies = policies;
    }

    public void validate(Order order) {
        policies.forEach(policy -> policy.validate(order));
    }
}
