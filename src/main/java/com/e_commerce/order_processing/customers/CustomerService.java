package com.e_commerce.order_processing.customers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepository repo;

    public boolean isFraudCustomer(Customer customer) {
        return false;
    }

    public Customer findById(String id) {
        return repo.findById(id).orElse(null);
    }
}
