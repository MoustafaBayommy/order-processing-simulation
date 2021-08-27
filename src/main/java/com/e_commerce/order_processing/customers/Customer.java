package com.e_commerce.order_processing.customers;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
        return getId().equals(other.getId());
    }
}
