package com.e_commerce.order_processing.orders;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BasketItem {
    @NotEmpty
    @NotNull
    String item;
    @Min(1)
    @NotNull
    int amount;
}
