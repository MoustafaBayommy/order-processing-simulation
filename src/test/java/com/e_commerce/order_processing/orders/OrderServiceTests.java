package com.e_commerce.order_processing.orders;

import com.e_commerce.order_processing.customers.Customer;
import com.e_commerce.order_processing.customers.CustomerService;
import com.e_commerce.order_processing.items.ItemService;
import com.e_commerce.order_processing.payments.PaymentService;
import com.e_commerce.order_processing.util.HttpException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class OrderServiceTests {

    @MockBean
    ItemService itemService;
    @MockBean
    PaymentService paymentService;
    @MockBean
    CustomerService customerService;
    @MockBean
    OrderRepository repo;
    @MockBean
    OrderValidator orderValidator;

    @Autowired
    OrderService service;

    @Before
    public void setUp() {

        //mock customers
        Customer mockedUser = new Customer();
        mockedUser.setId("mockedUser");
        mockedUser.setName("cusomer-name");
        Mockito.when(customerService.findById(mockedUser.getId()))
                .thenReturn(mockedUser);


        //mock items
        OrderItem item1 = new OrderItem();
        item1.setItemId("item1");
        item1.setPrice(150);
        item1.setAmount(3);
        Mockito.when(itemService.lookupForBasketItems(Matchers.any()))
                .thenReturn(Arrays.asList(item1));

    }

    @Test(expected = HttpException.class)
    public void orderProcessShouldThroughExcptionInInvalidCustomer() {
        OrderDto dto = new OrderDto();
        dto.setCustomerId("NON_EXISTEDUser");
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentMethod("CASH");
        dto.setPaymentDetails(paymentDetails);
        BasketItem firstItem = new BasketItem();
        firstItem.setItem("fackItemId");
        firstItem.setAmount(2);
        dto.setBasket(new HashSet<>(Arrays.asList(firstItem)));
        dto.setShippingAddress("1st cairo , egypt");

        this.service.processOrder(dto);
    }


    @Test
    public void orderProcessShouldCompletedSucessfully() {
        OrderDto dto = new OrderDto();
        dto.setCustomerId("mockedUser");
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentMethod("CASH");
        dto.setPaymentDetails(paymentDetails);
        BasketItem firstItem = new BasketItem();
        firstItem.setItem("fackItemId");
        firstItem.setAmount(2);
        dto.setBasket(new HashSet<>(Arrays.asList(firstItem)));
        dto.setShippingAddress("1st cairo , egypt");
        this.service.processOrder(dto);
    }


    @Test
    public void testGetBasketTotal() {
      Order order=new Order();
      OrderItem item1=new OrderItem();
      item1.setAmount(3);
      item1.setPrice(200);

        OrderItem item2=new OrderItem();
        item2.setAmount(1);
        item2.setPrice(150);
        order.setItems(Arrays.asList(item1,item2));
     float totalPrice=   this.service.getBasketTotal(order);
        assertThat(totalPrice).isEqualTo(3*200+1*150);
    }
}
