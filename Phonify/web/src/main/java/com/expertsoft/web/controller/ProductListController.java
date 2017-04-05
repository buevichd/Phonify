package com.expertsoft.web.controller;

import com.expertsoft.core.model.interfaces.PhoneDao;
import com.expertsoft.core.service.ShoppingCartService;
import com.expertsoft.web.form.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.expertsoft.web.controller.constants.ControllerConstants.ATTRIBUTE_MINI_CART;

@Controller
public class ProductListController {

    private static final String VIEW_NAME_PRODUCT_LIST = "productList";

    private static final String ATTRIBUTE_PRODUCTS = "products";
    private static final String ATTRIBUTE_CART_ITEM = "cartItem";

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(path = {"/productList", "/"}, method = RequestMethod.GET)
    public String productList(Model model) {
        model.addAttribute(ATTRIBUTE_PRODUCTS, phoneDao.findAll());
        model.addAttribute(ATTRIBUTE_CART_ITEM, new CartItem());
        model.addAttribute(ATTRIBUTE_MINI_CART, shoppingCartService.getMiniCart());
        return VIEW_NAME_PRODUCT_LIST;
    }

}
