
package com.userserver.item.repository;

import com.userserver.item.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findBySellerId(Long sellerId);
}
