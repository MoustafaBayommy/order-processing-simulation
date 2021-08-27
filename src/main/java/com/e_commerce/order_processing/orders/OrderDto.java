package com.e_commerce.order_processing.orders;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class OrderDto {

    @NotEmpty(message = "order should have one item atleast at basket")
    @Valid
    Set<BasketItem> basket;
    @NotNull
    @Valid
    PaymentDetails paymentDetails;
    @NotEmpty
    String customerId;
    @NotEmpty
    String shippingAddress;

}
