package com.e_commerce.order_processing.shipping;

import com.e_commerce.order_processing.orders.events.OrderCreationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);

    @EventListener(classes = OrderCreationEvent.class)
    public void startShippingProcess(OrderCreationEvent event){
        log.info(" *******start shipping process  for order  "+event.getOrder().toString());
    }

}
