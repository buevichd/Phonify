package com.expertsoft.core.model.interfaces;

import com.expertsoft.core.entity.OrderItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface OrderItemDao extends Dao<OrderItem> {

    @NotNull List<OrderItem> getOrderItemsByOrderKey(@NotNull Long orderKey);
}
