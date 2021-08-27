package com.e_commerce.order_processing.orders.validationRules;

import com.e_commerce.order_processing.items.ItemRepository;
import com.e_commerce.order_processing.items.ItemService;
import com.e_commerce.order_processing.orders.Order;
import com.e_commerce.order_processing.orders.OrderDto;
import com.e_commerce.order_processing.util.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemsAvaliablityPolicy implements OrderPolicy{
    @Autowired
    ItemService itemService;

    @Override
    public void validate(Order order) throws HttpException {
//        itemService.checkAvaliblity(order.getBasket())
    }
}
