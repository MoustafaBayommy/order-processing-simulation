package com.e_commerce.order_processing.orders;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    private String itemId;

    private float price;
    private int amount;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof OrderItem)) {
            return false;
        }
        OrderItem other = (OrderItem) obj;
        return getItemId().equals(other.getItemId());
    }
}
