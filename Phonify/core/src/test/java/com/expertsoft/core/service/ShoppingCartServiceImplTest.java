package com.expertsoft.core.service;

import com.expertsoft.core.cart.MiniCart;
import com.expertsoft.core.cart.ShoppingCart;
import com.expertsoft.core.entity.Phone;
import com.expertsoft.core.model.interfaces.PhoneDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config.xml")
@ActiveProfiles({"test", "default"})
@Transactional
public class ShoppingCartServiceImplTest {

    private static final String PHONE_MODEL = "Model";
    private static final BigDecimal PHONE_PRICE = BigDecimal.valueOf(1000);
    private static final Long FIRST_QUANTITY = 2L;
    private static final Long SECOND_QUANTITY = 3L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smit";
    private static final String DELIVERY_ADDRESS = "Wall st. 92";
    private static final String PHONE_NUMBER = "291234567";

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private PhoneDao phoneDao;

    private Phone phone;

    @Before
    public void setUp() {
        initShoppingCart();
        initPhone();
        shoppingCartService.clearShoppingCart();
    }

    @After
    public void finalizeTest() {
        shoppingCartService.clearShoppingCart();
    }

    private void initShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        shoppingCart.setFirstName(FIRST_NAME);
        shoppingCart.setLastName(LAST_NAME);
        shoppingCart.setDeliveryAddress(DELIVERY_ADDRESS);
        shoppingCart.setContactPhoneNo(PHONE_NUMBER);
    }

    private void initPhone() {
        phone = new Phone(PHONE_MODEL, PHONE_PRICE);
        phoneDao.create(phone);
    }

    @Test
    public void shouldReturnEmptyMiniCart() {
        MiniCart miniCart = shoppingCartService.getMiniCart();

        Assert.assertEquals(Long.valueOf(0L), miniCart.getItemCount());
        Assert.assertEquals(BigDecimal.ZERO, miniCart.getAmount());
    }

    @Test
    public void shouldAddFirstOrderItem() {
        shoppingCartService.addOrderItem(phone.getKey(), FIRST_QUANTITY);
        MiniCart miniCart = shoppingCartService.getMiniCart();

        BigDecimal expectedTotalPrice = phone.getPrice().multiply(BigDecimal.valueOf(FIRST_QUANTITY))
                .add(deliveryService.getDeliveryPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals(FIRST_QUANTITY, miniCart.getItemCount());
        Assert.assertEquals(expectedTotalPrice, shoppingCartService.getShoppingCart().getTotalPrice());
    }

    @Test
    public void shouldMergeTwoOrderItems() {
        shoppingCartService.addOrderItem(phone.getKey(), FIRST_QUANTITY);
        shoppingCartService.addOrderItem(phone.getKey(), SECOND_QUANTITY);
        MiniCart miniCart = shoppingCartService.getMiniCart();

        BigDecimal expectedAmount = phone.getPrice().multiply(BigDecimal.valueOf(FIRST_QUANTITY + SECOND_QUANTITY))
                .add(deliveryService.getDeliveryPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals(Long.valueOf(FIRST_QUANTITY + SECOND_QUANTITY), miniCart.getItemCount());
        Assert.assertEquals(expectedAmount, shoppingCartService.getShoppingCart().getTotalPrice());
    }

    @Test
    public void shouldDeleteOrderItem() {
        shoppingCartService.addOrderItem(phone.getKey(), FIRST_QUANTITY);
        shoppingCartService.deleteOrderItem(phone.getKey());

        Assert.assertEquals(0, shoppingCartService.getShoppingCart().getOrderItems().size());
    }

    @Test
    public void shouldUpdateOrderItems() {
        shoppingCartService.addOrderItem(phone.getKey(), FIRST_QUANTITY);

        Map<Long, Long> phoneKeyToQuantityMap = new LinkedHashMap<>();
        phoneKeyToQuantityMap.put(phone.getKey(), SECOND_QUANTITY);
        shoppingCartService.updateOrderItems(phoneKeyToQuantityMap);

        Assert.assertEquals(SECOND_QUANTITY, shoppingCartService.getShoppingCart().getOrderItems().get(0).getQuantity());
    }

}
