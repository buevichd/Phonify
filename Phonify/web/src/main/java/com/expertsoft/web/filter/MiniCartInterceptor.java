package com.expertsoft.web.filter;

import com.expertsoft.core.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MiniCartInterceptor extends HandlerInterceptorAdapter {

    private static final String ATTRIBUTE_MINI_CART = "miniCart";

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject(ATTRIBUTE_MINI_CART, shoppingCartService.getMiniCart());
    }
}
