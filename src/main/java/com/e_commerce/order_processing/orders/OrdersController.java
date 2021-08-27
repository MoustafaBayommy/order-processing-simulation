package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.orders.events.OrderCreationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    OrderService service;
    @Autowired
    ApplicationEventPublisher publisher;
    @PostMapping()
    public Order postOrder(@Valid @RequestBody OrderDto dto) {
       Order order= service.processOrder(dto);
        //publish an event for shipping and notifications
        publisher.publishEvent(new OrderCreationEvent(this,order,"created"));
        return order;
    }


}
