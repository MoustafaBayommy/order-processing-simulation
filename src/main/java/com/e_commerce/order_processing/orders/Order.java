package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.customers.Customer;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    private String id;
    @ManyToOne
    private Customer customer;
    private String status;
    private String paymentMethod;
    @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.PERSIST)
    private List<OrderItem> items;

    public static OrderBuilder builer() {
        return new OrderBuilder();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Order)) {
            return false;
        }
        Order other = (Order) obj;
        return getId().equals(other.getId());
    }

    @Override
    public String toString() {
        if (this == null) {
            return null;
        }
        return "Order {" +
                "id='" + id + '\'' +
                ", customer=" + customer.toString() +
                ", status='" + status + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
