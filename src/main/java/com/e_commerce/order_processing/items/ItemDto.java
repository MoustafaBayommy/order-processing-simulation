package com.e_commerce.order_processing.items;

import javax.validation.constraints.Min;

public class ItemDto {

    @Min(value = 3, message = "minumim 0")
    public long amount;
}
