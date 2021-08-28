package com.e_commerce.order_processing.items;

import com.e_commerce.order_processing.customers.Customer;
import com.e_commerce.order_processing.customers.CustomerService;
import com.e_commerce.order_processing.items.Item;
import com.e_commerce.order_processing.items.ItemRepository;
import com.e_commerce.order_processing.items.ItemService;
import com.e_commerce.order_processing.orders.BasketItem;
import com.e_commerce.order_processing.orders.OrderItem;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ItemsServiceTests {

    @MockBean
    ItemRepository repo;

    @Autowired
    ItemService service;

    @Before
    public void setUp() {

        //mock items
        Item mockedItem = new Item();
        mockedItem.setId("itemValidId");
        mockedItem.setPrice(10);
        mockedItem.setStockAmount(122);
        Item mockedItem2 = new Item();
        mockedItem2.setId("itemValidId2");
        mockedItem2.setPrice(10);
        mockedItem2.setStockAmount(2);
        Mockito.when(repo.findById(mockedItem.getId()))
                .thenReturn(Optional.of(mockedItem));
        Mockito.when(repo.findById(mockedItem2.getId()))
                .thenReturn(Optional.of(mockedItem2));

    }

    @Test()
    public void testlookupForBasketItems() {
   BasketItem item=new BasketItem();
   item.setItem("itemValidId");
   item.setAmount(10);

        BasketItem item2=new BasketItem();
        item2.setItem("itemValidId2");
        item2.setAmount(1);

       List<OrderItem> items= this.service.lookupForBasketItems(new HashSet<>(Arrays.asList(item,item2)));
        assertThat(items.size()).isEqualTo(2);
    }

    @Test(expected = HttpException.class)
    public void testlookupForBasketItemsIfItemUnAvalible() {
        BasketItem item2=new BasketItem();
        item2.setItem("itemValidId2");
        item2.setAmount(5);

        List<OrderItem> items= this.service.lookupForBasketItems(new HashSet<>(Arrays.asList(item2)));
        assertThat(items.size()).isEqualTo(2);
    }
}
