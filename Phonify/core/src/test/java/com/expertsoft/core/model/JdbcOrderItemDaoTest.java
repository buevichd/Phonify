package com.expertsoft.core.model;

import com.expertsoft.core.entity.OrderItem;
import com.expertsoft.core.entity.Phone;
import com.expertsoft.core.model.interfaces.PhoneDao;
import com.expertsoft.core.model.jdbc.JdbcOrderItemDao;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config.xml")
@ActiveProfiles({"test", "default"})
@Transactional
public class JdbcOrderItemDaoTest {

    private static final String PHONE_MODEL = "Model";
    private static final BigDecimal PHONE_PRICE = BigDecimal.valueOf(1000);
    private static final Long QUANTITY = 5L;
    private static final Long ORDER_KEY = 7L;

    @Autowired
    private JdbcOrderItemDao jdbcOrderItemDao;


    private PhoneDao phoneDao;

    private Phone phone;

    @Before
    public void setUp() {
        initPhone();
    }

    private void initPhone() {
        phone = new Phone(PHONE_MODEL, PHONE_PRICE);
        phoneDao.create(phone);
    }

    @Test
    public void shouldCreateOrderItem() {
        OrderItem newOrderItem = new OrderItem(phone, QUANTITY, ORDER_KEY);

        jdbcOrderItemDao.create(newOrderItem);
        OrderItem createdOrderItem = jdbcOrderItemDao.get(newOrderItem.getKey());

        assertTrue(EqualsBuilder.reflectionEquals(newOrderItem, createdOrderItem));
    }

    @Test
    public void shouldUpdateOrderItem() {
        OrderItem newOrderItem = new OrderItem(phone, QUANTITY, ORDER_KEY);
        jdbcOrderItemDao.create(newOrderItem);
        newOrderItem.setQuantity(QUANTITY + 1);

        jdbcOrderItemDao.update(newOrderItem);
        OrderItem createdOrderItem = jdbcOrderItemDao.get(newOrderItem.getKey());

        assertTrue(EqualsBuilder.reflectionEquals(newOrderItem, createdOrderItem));
    }

    @Test
    public void shouldFindAll() {
        OrderItem newOrderItem = new OrderItem(phone, QUANTITY, ORDER_KEY);
        jdbcOrderItemDao.create(newOrderItem);

        List<OrderItem> allItems = jdbcOrderItemDao.findAll();

        assertEquals(1, allItems.size());
        assertTrue(EqualsBuilder.reflectionEquals(newOrderItem, allItems.get(0)));
    }

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }
}
