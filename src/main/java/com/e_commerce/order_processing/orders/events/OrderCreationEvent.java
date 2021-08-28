package com.e_commerce.order_processing.orders.events;

import com.e_commerce.order_processing.orders.Order;
import org.springframework.context.ApplicationEvent;

public class OrderCreationEvent extends ApplicationEvent {
    private final String message;
    private final Order order;

    public OrderCreationEvent(Object source, Order oder, String message) {
        super(source);
        this.message = message;
        this.order = oder;
    }

    public String getMessage() {
        return message;
    }

    public Order getOrder() {
        return this.order;
    }
}
