package com.expertsoft.web.controller;

import com.expertsoft.core.model.interfaces.PhoneDao;
import com.expertsoft.core.service.ShoppingCartService;
import com.expertsoft.web.form.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.expertsoft.web.controller.constants.ControllerConstants.ATTRIBUTE_CART_ITEM;
import static com.expertsoft.web.controller.constants.ControllerConstants.ATTRIBUTE_MINI_CART;

@Controller
@RequestMapping("/productDetails")
public class ProductDetailsController {

    private static final String VIEW_NAME_PRODUCT_DETAILS = "productDetails";

    private static final String ATTRIBUTE_PRODUCT = "product";

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("{key}")
    public String getProductDetails(Model model, @PathVariable("key") Long key) {
        model.addAttribute(ATTRIBUTE_PRODUCT, phoneDao.get(key));
        model.addAttribute(ATTRIBUTE_CART_ITEM, new CartItem());
        model.addAttribute(ATTRIBUTE_MINI_CART, shoppingCartService.getMiniCart());
        return VIEW_NAME_PRODUCT_DETAILS;
    }

}
