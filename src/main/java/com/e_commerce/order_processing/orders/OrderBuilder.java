package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.customers.Customer;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        this.order = new Order();
    }


    public OrderBuilder id(UUID id) {
        this.order.setId(id.toString());
        return this;
    }

    public OrderBuilder customer(Customer customer) {
        this.order.setCustomer(customer);
        return this;
    }


    public OrderBuilder paymentDetails(PaymentDetails paymentDetails) {
        this.order.setPaymentMethod(paymentDetails.getPaymentMethod());
        return this;
    }

    public OrderBuilder basket(List<OrderItem> items) {
        this.order.setItems(items);
        return this;
    }

    public OrderBuilder status(OrderStatus status) {
        this.order.setStatus(status.name());
        return this;
    }

    public Order build() {
        return this.order;
    }

}
