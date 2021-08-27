package com.e_commerce.order_processing.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

  List<Item>   findByIdAndStockAmountGreaterThanEqual(String id, int amount);
  @Modifying
  @Query("update Item i set i.stockAmount = i.stockAmount -?1 where i.id = ?2")
  void decrementBalanceBy(int amount, String id);
}
