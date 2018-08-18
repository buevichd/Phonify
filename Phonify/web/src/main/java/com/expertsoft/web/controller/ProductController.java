package com.expertsoft.web.controller;

import com.expertsoft.core.model.interfaces.PhoneDao;
import com.expertsoft.web.form.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {

    private static final String VIEW_NAME_PRODUCT_DETAILS = "productDetails";
    private static final String VIEW_NAME_PRODUCT_LIST = "productList";

    private static final String ATTRIBUTE_PRODUCTS = "products";
    private static final String ATTRIBUTE_CART_ITEM = "cartItem";
        private static final String ATTRIBUTE_PRODUCT = "product";

    @Autowired
    private PhoneDao phoneDao;

    @RequestMapping(path = {"/productList", "/"}, method = RequestMethod.GET)
    public String productList(Model model) {
        model.addAttribute(ATTRIBUTE_PRODUCTS, phoneDao.findAll());
        model.addAttribute(ATTRIBUTE_CART_ITEM, new CartItem());
        return VIEW_NAME_PRODUCT_LIST;
    }

    @RequestMapping("/productDetails/{key}")
    public String getProductDetails(Model model, @PathVariable("key") Long key) {
        model.addAttribute(ATTRIBUTE_PRODUCT, phoneDao.get(key));
        model.addAttribute(ATTRIBUTE_CART_ITEM, new CartItem());
        return VIEW_NAME_PRODUCT_DETAILS;
    }

    public void doEverything() {
        System.out.println("Hello");
    }

}
