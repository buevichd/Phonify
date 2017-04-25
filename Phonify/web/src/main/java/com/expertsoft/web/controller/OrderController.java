package com.expertsoft.web.controller;

import com.expertsoft.core.cart.ShoppingCart;
import com.expertsoft.core.entity.Order;
import com.expertsoft.core.entity.OrderItem;
import com.expertsoft.core.model.interfaces.OrderDao;
import com.expertsoft.core.service.ShoppingCartService;
import com.expertsoft.web.form.order.OrderContactInfoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String VIEW_NAME_ORDER = "order";
    private static final String VIEW_NAME_CONFIRMATION = "confirmation";

    public static final String ATTRIBUTE_ORDER = "order";
    private static final String ATTRIBUTE_ORDER_CONTACT_INFO_FORM = "orderContactInfoForm";

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) {
        model.addAttribute(ATTRIBUTE_ORDER_CONTACT_INFO_FORM, createOrderContactInfoForm());
        model.addAttribute(ATTRIBUTE_ORDER, shoppingCartService.getShoppingCart());
        return VIEW_NAME_ORDER;
    }

    private OrderContactInfoForm createOrderContactInfoForm() {
        OrderContactInfoForm form = new OrderContactInfoForm();
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        form.setFirstName(shoppingCart.getFirstName());
        form.setLastName(shoppingCart.getLastName());
        form.setDeliveryAddress(shoppingCart.getDeliveryAddress());
        form.setContactPhoneNo(shoppingCart.getContactPhoneNo());
        return form;
    }

    @RequestMapping(path = "submit", method = RequestMethod.POST)
    public String submitOrder(@Valid OrderContactInfoForm orderContactInfoForm , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ATTRIBUTE_ORDER_CONTACT_INFO_FORM, orderContactInfoForm);
            model.addAttribute(ATTRIBUTE_ORDER, shoppingCartService.getShoppingCart());
            return VIEW_NAME_ORDER;
        }
        updateOrderContactInfo(orderContactInfoForm);
        Order order = createOrderFromShoppingCart();
        order.setDeliveryStatus(Order.DeliveryStatus.IN_PROCESS);
        orderDao.create(order);
        shoppingCartService.clearShoppingCart();
        return "redirect:confirmation/" + order.getKey();
    }

    private void updateOrderContactInfo(OrderContactInfoForm orderContactInfoForm) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        shoppingCart.setFirstName(orderContactInfoForm.getFirstName());
        shoppingCart.setLastName(orderContactInfoForm.getLastName());
        shoppingCart.setDeliveryAddress(orderContactInfoForm.getDeliveryAddress());
        shoppingCart.setContactPhoneNo(orderContactInfoForm.getContactPhoneNo());
    }

    private Order createOrderFromShoppingCart() {
        Order order = new Order();
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        for (OrderItem orderItem : shoppingCart.getOrderItems()) {
            order.getOrderItems().add(new OrderItem(orderItem.getPhone(), orderItem.getQuantity()));
        }
        order.setFirstName(shoppingCart.getFirstName());
        order.setLastName(shoppingCart.getLastName());
        order.setDeliveryAddress(shoppingCart.getDeliveryAddress());
        order.setContactPhoneNo(shoppingCart.getContactPhoneNo());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setDeliveryPrice(shoppingCart.getDeliveryPrice());
        order.setSubtotal(shoppingCart.getSubtotal());
        return order;
    }

    @RequestMapping(path = "confirmation/{orderKey}", method = RequestMethod.GET)
    public String getConfirmedOrder(@PathVariable("orderKey") Long orderKey, Model model) {
        Order order = orderDao.get(orderKey);
        model.addAttribute(ATTRIBUTE_ORDER, order);
        return VIEW_NAME_CONFIRMATION;
    }

}
