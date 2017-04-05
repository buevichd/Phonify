package com.expertsoft.core.service;

import com.expertsoft.core.cart.MiniCart;
import com.expertsoft.core.cart.ShoppingCart;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ShoppingCartService {
    void addOrderItem(@NotNull Long phoneKey, @NotNull Long quantity);
    void deleteOrderItem(@NotNull Long phoneKey);
    void updateOrderItems(@NotNull Map<Long, Long> phoneKeyToQuantityMap);
    void clearShoppingCart();
    @NotNull ShoppingCart getShoppingCart();
    @NotNull MiniCart getMiniCart();
}
