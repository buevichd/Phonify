package com.expertsoft.core.service;

import com.expertsoft.core.cart.MiniCart;
import com.expertsoft.core.cart.ShoppingCart;
import com.expertsoft.core.entity.OrderItem;
import com.expertsoft.core.entity.Phone;
import com.expertsoft.core.model.interfaces.PhoneDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component("shoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ShoppingCart shoppingCart;

    @Autowired
    private PhoneDao phoneDao;

    @Override
    public void addOrderItem(@NotNull Long phoneKey, @NotNull Long quantity) {
        if (isPhoneAlreadyInOrder(phoneKey)) {
            OrderItem existingOrderItem = getOrderItem(phoneKey);
            existingOrderItem.setQuantity(existingOrderItem.getQuantity() + quantity);
        } else {
            Phone phone = phoneDao.get(phoneKey);
            shoppingCart.getOrderItems().add(new OrderItem(phone, quantity));
        }
        updatePrices();
    }

    private boolean isPhoneAlreadyInOrder(@NotNull Long key) {
        for (OrderItem orderItem : shoppingCart.getOrderItems()) {
            if (orderItem.getPhone().getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private OrderItem getOrderItem(@NotNull Long key) {
        for (OrderItem orderItem : shoppingCart.getOrderItems()) {
            if (orderItem.getPhone().getKey().equals(key)) {
                return orderItem;
            }
        }
        throw new IllegalArgumentException("There is no phone:" + key + " in shoppingCart:" + getShoppingCart().getKey());
    }

    @Override
    public void deleteOrderItem(@NotNull Long key) {
        OrderItem orderItem = getOrderItem(key);
        shoppingCart.getOrderItems().remove(orderItem);
        updatePrices();
    }

    @Override
    public void updateOrderItems(@NotNull Map<Long, Long> phoneKeyToQuantityMap) {
        for (Long key : phoneKeyToQuantityMap.keySet()) {
            OrderItem existingOrderItem = getOrderItem(key);
            existingOrderItem.setQuantity(phoneKeyToQuantityMap.get(key));
        }
        updatePrices();
    }

    @Override
    public void clearShoppingCart() {
        shoppingCart.getOrderItems().clear();
        updatePrices();
    }

    private void updatePrices() {
        shoppingCart.setSubtotal(calculateSubtotal());
        shoppingCart.setItemCount(calculateItemCount());
        shoppingCart.setDeliveryPrice(deliveryService.getDeliveryPrice());
        shoppingCart.setTotalPrice(shoppingCart.getSubtotal().add(shoppingCart.getDeliveryPrice()));
    }

    private Long calculateItemCount() {
        long itemCount = 0;
        for (OrderItem orderItem : shoppingCart.getOrderItems()) {
            itemCount += orderItem.getQuantity();
        }
        return itemCount;
    }

    private BigDecimal calculateSubtotal() {
        BigDecimal amount = BigDecimal.ZERO;
        for (OrderItem orderItem : shoppingCart.getOrderItems()) {
            Phone phone = orderItem.getPhone();
            Long quantity = orderItem.getQuantity();
            amount = amount.add(phone.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        return amount;
    }

    @NotNull
    @Override
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    @NotNull
    @Override
    public MiniCart getMiniCart() {
        return new MiniCart(shoppingCart.getItemCount(), shoppingCart.getSubtotal());
    }

}
