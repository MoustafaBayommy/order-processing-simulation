package com.e_commerce.order_processing.payments;

public class PaymentHandlingException extends RuntimeException {
    public PaymentHandlingException(String message) {
        super(message);
    }
}
