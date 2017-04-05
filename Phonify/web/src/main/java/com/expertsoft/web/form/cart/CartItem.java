package com.expertsoft.web.form.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {

    private Long phoneKey;

    @NotNull()
    @Min(1)
    private Long quantity;

    public CartItem() {
    }

    public CartItem(Long phoneKey, Long quantity) {
        this.phoneKey = phoneKey;
        this.quantity = quantity;
    }

    public Long getPhoneKey() {
        return phoneKey;
    }

    public void setPhoneKey(Long phoneKey) {
        this.phoneKey = phoneKey;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
