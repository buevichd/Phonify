package com.expertsoft.core.cart;

import java.math.BigDecimal;

public class MiniCart {

    private Long itemCount = 0L;
    private BigDecimal amount = BigDecimal.ZERO;

    public MiniCart(Long itemCount, BigDecimal amount) {
        this.itemCount = itemCount;
        this.amount = amount;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
