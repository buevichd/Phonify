package com.expertsoft.web.form.cart;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class CartItemListForm {

    @Valid
    private List<CartItem> cartItemList = new ArrayList<>();

    public CartItemListForm() {
    }

    public CartItemListForm(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
