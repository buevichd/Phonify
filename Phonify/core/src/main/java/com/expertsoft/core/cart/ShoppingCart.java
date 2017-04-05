package com.expertsoft.core.cart;

import com.expertsoft.core.entity.Order;

public class ShoppingCart extends Order {

    private Long itemCount = 0L;

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }
}
