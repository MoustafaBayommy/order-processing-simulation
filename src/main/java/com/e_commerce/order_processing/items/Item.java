package com.e_commerce.order_processing.items;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "items")
public class Item {
    @Id
    private String id;
    @Column()
    private String name;
    private String description;
    @Column(nullable = false)
    private int stockAmount = 0;
    private float price = 0;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Item)) {
            return false;
        }
        Item other = (Item) obj;
        return getId().equals(other.getId());
    }
}
