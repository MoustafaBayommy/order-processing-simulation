package com.e_commerce.order_processing.orders;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class PaymentDetails {
    @Pattern(regexp = "CASH|CREDIT", flags = Pattern.Flag.CASE_INSENSITIVE, message = "payment method should be Cash or credit ")
    String paymentMethod;
    @NotEmpty
    String cardNumber;
    @NotEmpty
    String cvc;
    @NotEmpty
    String expMonth;
    @NotEmpty
    String expYear;


}
