package com.expertsoft.core.entity;

public class OrderItem {

    private Long key;

    private Phone phone;

    private Long quantity;

    private Long orderKey;

    public OrderItem() {
    }

    public OrderItem(Phone phone, Long quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public OrderItem(Phone phone, Long quantity, Long orderKey) {
        this.phone = phone;
        this.quantity = quantity;
        this.orderKey = orderKey;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(Long orderKey) {
        this.orderKey = orderKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) o;

        return key != null ? key.equals(orderItem.key) : orderItem.key == null;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }
}
