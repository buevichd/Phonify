package com.expertsoft.core.model;

import com.expertsoft.core.entity.Order;
import com.expertsoft.core.entity.OrderItem;
import com.expertsoft.core.entity.Phone;
import com.expertsoft.core.model.interfaces.OrderItemDao;
import com.expertsoft.core.model.interfaces.PhoneDao;
import com.expertsoft.core.model.jdbc.JdbcOrderDao;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config.xml")
@ActiveProfiles({"test", "default"})
@Transactional
public class JdbcOrderDaoTest {

    private static final BigDecimal SUBTOTAL = new BigDecimal("12.50");
    private static final BigDecimal DELIVERY_PRICE = new BigDecimal("2.50");
    private static final BigDecimal TOTAL_PRICE = SUBTOTAL.add(DELIVERY_PRICE);
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smit";
    private static final String DELIVERY_ADDRESS = "Wall st. 92";
    private static final String PHONE_NUMBER = "291234567";
    private static final String PHONE_MODEL = "Samsung";
    private static final BigDecimal PHONE_PRICE = BigDecimal.TEN;
    private static final Long QUANTITY = 5L;

    @Autowired
    private JdbcOrderDao jdbcOrderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private PhoneDao phoneDao;

    private Phone phone;
    private OrderItem orderItem;
    private Order order;

    @Before
    public void setUp() {
        initPhone();
        initOrderItem();
        initOrder();
    }

    private void initPhone() {
        phone = new Phone(PHONE_MODEL, PHONE_PRICE);
        phoneDao.create(phone);
    }

    private void initOrderItem() {
        orderItem = new OrderItem(phone, QUANTITY);
    }

    private void initOrder() {
        order = new Order();
        order.setSubtotal(SUBTOTAL);
        order.setDeliveryPrice(DELIVERY_PRICE);
        order.setTotalPrice(TOTAL_PRICE);
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setContactPhoneNo(PHONE_NUMBER);
        order.setOrderItems(Collections.singletonList(orderItem));
        order.setDeliveryStatus(Order.DeliveryStatus.INITIAL);
    }

    @Test
    public void shouldCreateOrder() {
        jdbcOrderDao.create(order);

        Order createdOrder = jdbcOrderDao.get(order.getKey());
        assertTrue(EqualsBuilder.reflectionEquals(order, createdOrder));
    }

    @Test
    public void shouldUpdateOrder() {
        jdbcOrderDao.create(order);
        order.setDeliveryPrice(DELIVERY_PRICE.add(BigDecimal.ONE));
        order.getOrderItems().get(0).setQuantity(QUANTITY + 1);

        jdbcOrderDao.update(order);

        Order createdOrder = jdbcOrderDao.get(order.getKey());
        assertTrue(EqualsBuilder.reflectionEquals(order, createdOrder));
    }

    @Test
    public void shouldGetAllOrders() {
        jdbcOrderDao.create(order);

        List<Order> allOrders = jdbcOrderDao.findAll();

        assertEquals(1, allOrders.size());
        assertTrue(EqualsBuilder.reflectionEquals(order, allOrders.get(0)));
    }
}
