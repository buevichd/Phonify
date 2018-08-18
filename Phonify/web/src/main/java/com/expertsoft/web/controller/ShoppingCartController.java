package com.expertsoft.web.controller;

import com.expertsoft.core.cart.ShoppingCart;
import com.expertsoft.core.entity.OrderItem;
import com.expertsoft.core.service.ShoppingCartService;
import com.expertsoft.web.cart.AjaxCartResponse;
import com.expertsoft.web.cart.ErrorMessage;
import com.expertsoft.web.form.cart.CartItem;
import com.expertsoft.web.form.cart.CartItemListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private static final String VIEW_NAME_SHOPPING_CART = "cart";
    public static final String ATTRIBUTE_SHOPPING_CART = "shoppingCart";

    private static final String CART_ITEM_LIST_FORM = "cartItemListForm";
    private static final String FIELD_QUANTITY = "quantity";

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private MessageSource messageSource;
asdgsdg
    @ModelAttribute(ATTRIBUTE_SHOPPING_CART)
    public ShoppingCart getShoppingCartAttribute() {
        return shoppingCartService.getShoppingCart();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getShoppingCart(Model model) {
        model.addAttribute(CART_ITEM_LIST_FORM, getCartItemListForm());
        return VIEW_NAME_SHOPPING_CART;
    }

    private CartItemListForm getCartItemListForm() {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        List<CartItem> cartItemList = new ArrayList<>(shoppingCart.getOrderItems().size());
        for (OrderItem orderItem : shoppingCart.getOrderItems()) {
            cartItemList.add(new CartItem(orderItem.getPhone().getKey(), orderItem.getQuantity()));
        }
        return new CartItemListForm(cartItemList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateShoppingCart(@RequestParam(name = "delete", required = false) Long keyOfDeletedPhone, Model model,
                                     @Valid CartItemListForm cartItemListForm, BindingResult bindingResult) {
        if (keyOfDeletedPhone != null) {
            // "Delete" button was pressed.
            shoppingCartService.deleteOrderItem(keyOfDeletedPhone);
        } else {
            // "Update" button was presses.
            return updateCartItems(cartItemListForm, bindingResult, model);
        }
        return "redirect:/cart";
    }

    private String updateCartItems(CartItemListForm cartItemListForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CART_ITEM_LIST_FORM, cartItemListForm);
            model.addAttribute(ATTRIBUTE_SHOPPING_CART, shoppingCartService.getShoppingCart());
            return VIEW_NAME_SHOPPING_CART;
        }
        shoppingCartService.updateOrderItems(getPhoneKeyToQuantityMap(cartItemListForm.getCartItemList()));
        return "redirect:/cart";
    }

    private Map<Long, Long> getPhoneKeyToQuantityMap(List<CartItem> cartItemList) {
        Map<Long, Long> phoneKeyToQuantityMap = new HashMap<>(cartItemList.size());
        for (CartItem cartItem : cartItemList) {
            phoneKeyToQuantityMap.put(cartItem.getPhoneKey(), cartItem.getQuantity());
        }
        return phoneKeyToQuantityMap;
    }


    @ResponseBody
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public AjaxCartResponse addOrderItemToShoppingCart(@Valid CartItem cartItem, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            shoppingCartService.addOrderItem(cartItem.getPhoneKey(), cartItem.getQuantity());
        }
        AjaxCartResponse ajaxCartResponse = new AjaxCartResponse();
        ajaxCartResponse.setMiniCart(shoppingCartService.getMiniCart());
        ajaxCartResponse.setErrorMessage(getErrorMessage(cartItem, bindingResult));
        return ajaxCartResponse;
    }

    private ErrorMessage getErrorMessage(CartItem cartItem, BindingResult bindingResult) {
        ErrorMessage message = new ErrorMessage();
        message.setKey(cartItem.getPhoneKey());
        FieldError fieldError = bindingResult.getFieldError(FIELD_QUANTITY);
        if (fieldError != null) {
            message.setMessage(messageSource.getMessage(fieldError, null));
        }
        return message;
    }

}
