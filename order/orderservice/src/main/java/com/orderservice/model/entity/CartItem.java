package com.orderservice.model.entity;

import com.orderservice.global.util.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseEntity {
    @Id
    private Long id;
    private Long cartId;
    private Long itemId;
    private int quantity;
    private boolean isSelected;

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public CartItem(Long cartId, Long itemId, int quantity, boolean isSelected) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.isSelected = isSelected;
    }
}
