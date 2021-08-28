package com.e_commerce.order_processing.notifications;

import com.e_commerce.order_processing.orders.events.OrderCreationEvent;
import com.e_commerce.order_processing.shipping.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);


    @EventListener(classes = OrderCreationEvent.class)
    public void sendNotificationToCustomer(OrderCreationEvent event) {
        log.info(" *******send order ready for shiping notification to customer    " + event.getOrder().getCustomer().toString());
    }

}
