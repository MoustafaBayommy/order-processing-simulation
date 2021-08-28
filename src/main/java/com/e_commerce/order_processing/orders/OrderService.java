package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.customers.Customer;
import com.e_commerce.order_processing.customers.CustomerService;
import com.e_commerce.order_processing.items.ItemService;
import com.e_commerce.order_processing.payments.PaymentService;
import com.e_commerce.order_processing.util.HttpException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private ItemService itemService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderRepository repo;
    @Autowired
    private OrderValidator orderValidator;


    @Transactional
    public Order processOrder(OrderDto dto) {
        //check if customer is  a real one
        Customer customer = customerService.findById(dto.customerId);
        if (customer == null) {
            throw new HttpException(HttpStatus.FAILED_DEPENDENCY, "Customer not found");
        }
        List<OrderItem> itemList = itemService.lookupForBasketItems(dto.getBasket());

        //construct order object
        Order order = Order.builer()
                .id(UUID.randomUUID())
                .customer(customer)
                .paymentDetails(dto.paymentDetails)
                .status(OrderStatus.CREATED)
                .basket(itemList)
                .build();
        //validate order
        orderValidator.validate(order);
        this.repo.save(order);
        //update stock
        itemService.updateStock(dto.getBasket());
        //payment processing
        paymentService.deductOrderValue(dto.getPaymentDetails(), getBasketTotal(order));
        return order;
    }


    public void updateOrderStatus(Order order) {
        //use state pattern to move next
    }


    public void onOrderShipped(Order order) {
        //use state pattern to move next
    }

    public float getBasketTotal(Order order) {
        return order.getItems().stream().map(item -> item.getPrice() * item.getAmount())
                .reduce(0f, Float::sum);
    }
}
