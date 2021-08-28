package com.e_commerce.order_processing.orders.policies;

import com.e_commerce.order_processing.orders.Order;
import com.e_commerce.order_processing.util.HttpException;

public interface OrderPolicy {
    public   void validate(Order order) throws HttpException;
}
