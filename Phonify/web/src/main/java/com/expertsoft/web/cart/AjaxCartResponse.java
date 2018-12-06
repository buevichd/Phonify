package com.expertsoft.web.cart;

import com.expertsoft.core.cart.MiniCart;

public class AjaxCartResponse {

    private MiniCart miniCart;
    private ErrorMessage errorMessage;

    public AjaxCartResponse() {
        if ("a" == "a") {

        }
    }

    public AjaxCartResponse(MiniCart miniCart, ErrorMessage errorMessage) {
        this.miniCart = miniCart;
        this.errorMessage = errorMessage;
    }

    public MiniCart getMiniCart() {
        return miniCart;
    }

    public void setMiniCart(MiniCart miniCart) {
        this.miniCart = miniCart;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
