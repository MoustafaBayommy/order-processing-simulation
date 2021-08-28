package com.e_commerce.order_processing.items;

import com.e_commerce.order_processing.orders.BasketItem;
import com.e_commerce.order_processing.orders.OrderItem;
import com.e_commerce.order_processing.util.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ItemService {
    @Autowired
    ItemRepository repo;

    public void save() {
        Item product = new Item();
        product.setStockAmount(1);
        repo.save(product);
    }

    public List<Item> findAll() {
        return repo.findAll();
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<OrderItem> lookupForBasketItems(Set<BasketItem> basketItems) {
        List<OrderItem> items = new ArrayList<>();

        basketItems.forEach(basketItem -> {
            Item item = repo.findById(basketItem.getItem()).orElseGet(null);
            if (item == null) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "not valid itemId");
            }
            if (item.getStockAmount() < basketItem.getAmount()) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "unfortunately item" + item.getName() + " un available ");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(item.getId());
            orderItem.setAmount(basketItem.getAmount());
            orderItem.setPrice(item.getPrice());
            items.add(orderItem);
        });

        return items;
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void updateStock(Set<BasketItem> basketItems) {
        basketItems.forEach(basketItem -> {
            decrementStockAmount(basketItem.getItem(), basketItem.getAmount());
        });

    }

    private void decrementStockAmount(String id, int amount) {
        this.repo.decrementBalanceBy(amount, id);
    }
}
