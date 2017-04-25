package com.expertsoft.web.controller.admin;

import com.expertsoft.core.entity.Order;
import com.expertsoft.core.model.interfaces.OrderDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String VIEW_NAME_EDIT_ORDERS = "editOrders";
    private static final String ATTRIBUTE_ORDERS = "orders";
    private static final String ATTRIBUTE_TOTAL_PAGE_NUMBER = "totalPageNumber";

    @Value("${admin.order.ordersPerPage}")
    private int ordersPerPage;

    @Autowired
    private OrderDao orderDao;

    @RequestMapping(path = "/orders", method = RequestMethod.GET)
    public String getOrders(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Order> allOrders = orderDao.findAll();
        model.addAttribute(ATTRIBUTE_ORDERS, getOrdersByPageNumber(allOrders, pageNumber));
        model.addAttribute(ATTRIBUTE_TOTAL_PAGE_NUMBER, (allOrders.size() - 1) / ordersPerPage + 1);
        return VIEW_NAME_EDIT_ORDERS;
    }

    @NotNull
    private List<Order> getOrdersByPageNumber(List<Order> orders, int pageNumber) {
        int firstOrderIndex = (pageNumber - 1) * ordersPerPage;
        int lastOrderIndexPlusOne = Math.min(pageNumber * ordersPerPage, orders.size());
        return orders.subList(firstOrderIndex, lastOrderIndexPlusOne);
    }

    @RequestMapping(path = "/orders", method = RequestMethod.POST)
    public String markOrderAsDelivered(@RequestParam("key") Long key,
                                       @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        Order order = orderDao.get(key);
        order.setDeliveryStatus(Order.DeliveryStatus.DELIVERED);
        orderDao.update(order);
        return "redirect:/admin/orders?page=" + pageNumber;
    }

}
