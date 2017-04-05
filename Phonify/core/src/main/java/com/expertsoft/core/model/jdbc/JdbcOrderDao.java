package com.expertsoft.core.model.jdbc;

import com.expertsoft.core.entity.Order;
import com.expertsoft.core.entity.OrderItem;
import com.expertsoft.core.model.interfaces.OrderDao;
import com.expertsoft.core.model.interfaces.OrderItemDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("orderDao")
public class JdbcOrderDao extends NamedParameterJdbcDaoSupport implements OrderDao {

    private static final String GET_ORDER = "SELECT * FROM phonify_order WHERE phonify_order.key = :key";
    private static final String CREATE_ORDER = "INSERT INTO phonify_order " +
            "(subtotal, delivery_price, total_price, first_name, last_name, delivery_address, contact_phone_no) " +
            "VALUES (:subtotal, :delivery_price, :total_price, :first_name, :last_name, :delivery_address, :contact_phone_no)";
    private static final String UPDATE_ORDER = "UPDATE phonify_order SET subtotal=:subtotal, delivery_price=:delivery_price, " +
            "total_price=:total_price, first_name=:first_name, last_name=:last_name, delivery_address=:delivery_address," +
            "contact_phone_no=:contact_phone_no WHERE phonify_order.key = :key";
    private static final String GET_ALL_ORDERS = "SELECT * FROM phonify_order";

    private static final String KEY = "key";
    private static final String SUBTOTAL = "subtotal";
    private static final String DELIVERY_PRICE = "delivery_price";
    private static final String TOTAL_PRICE = "total_price";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String DELIVERY_ADDRESS = "delivery_address";
    private static final String CONTACT_PHONE_NO = "contact_phone_no";

    private OrderItemDao orderItemDao;

    @NotNull
    @Override
    public Order get(Long key) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(KEY, key);
        return getNamedParameterJdbcTemplate().queryForObject(GET_ORDER, map, ((resultSet, i) -> {
            Order order = new Order();
            order.setKey(resultSet.getLong(KEY));
            order.setSubtotal(resultSet.getBigDecimal(SUBTOTAL));
            order.setDeliveryPrice(resultSet.getBigDecimal(DELIVERY_PRICE));
            order.setTotalPrice(resultSet.getBigDecimal(TOTAL_PRICE));
            order.setFirstName(resultSet.getString(FIRST_NAME));
            order.setLastName(resultSet.getString(LAST_NAME));
            order.setDeliveryAddress(resultSet.getString(DELIVERY_ADDRESS));
            order.setContactPhoneNo(resultSet.getString(CONTACT_PHONE_NO));
            order.setOrderItems(orderItemDao.getOrderItemsByOrderKey(order.getKey()));
            return order;
        }));
    }

    @Override
    public void create(@NotNull Order order) {
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(getOrderFieldsMap(order));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(CREATE_ORDER, mapSqlParameterSource, keyHolder);
        order.setKey(keyHolder.getKey().longValue());
        createOrderItems(order);
    }

    private void createOrderItems(@NotNull Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrderKey(order.getKey());
            orderItemDao.create(orderItem);
        }
    }

    @Override
    public void update(@NotNull Order order) {
        Map<String, Object> map = getOrderFieldsMap(order);
        map.put(KEY, order.getKey());
        getNamedParameterJdbcTemplate().update(UPDATE_ORDER, map);
        updateOrderItems(order);
    }

    private void updateOrderItems(@NotNull Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemDao.update(orderItem);
        }
    }

    @NotNull
    private Map<String, Object> getOrderFieldsMap(@NotNull Order order) {
        Map<String, Object> map = new HashMap<>(7);
        map.put(SUBTOTAL, order.getSubtotal());
        map.put(DELIVERY_PRICE, order.getDeliveryPrice());
        map.put(TOTAL_PRICE, order.getTotalPrice());
        map.put(FIRST_NAME, order.getFirstName());
        map.put(LAST_NAME, order.getLastName());
        map.put(DELIVERY_ADDRESS, order.getDeliveryAddress());
        map.put(CONTACT_PHONE_NO, order.getContactPhoneNo());
        return map;
    }

    @NotNull
    @Override
    public List<Order> findAll() {
        Map<Long, Order> ordersMap = getOrdersMap();
        List<OrderItem> orderItems = orderItemDao.findAll();
        bindOrderItemsWithOrders(ordersMap, orderItems);
        return new ArrayList<>(ordersMap.values());
    }

    @NotNull
    private Map<Long, Order> getOrdersMap() {
        Map<Long, Order> ordersMap = new HashMap<>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(GET_ALL_ORDERS);
        for (Map<String, Object> row : rows) {
            Order order = new Order();
            order.setKey((Long) row.get(KEY));
            order.setSubtotal((BigDecimal) row.get(SUBTOTAL));
            order.setDeliveryPrice((BigDecimal) row.get(DELIVERY_PRICE));
            order.setTotalPrice((BigDecimal) row.get(TOTAL_PRICE));
            order.setFirstName((String) row.get(FIRST_NAME));
            order.setLastName((String) row.get(LAST_NAME));
            order.setDeliveryAddress((String) row.get(DELIVERY_ADDRESS));
            order.setContactPhoneNo((String) row.get(CONTACT_PHONE_NO));
            ordersMap.put(order.getKey(), order);
        }
        return ordersMap;
    }

    private void bindOrderItemsWithOrders(Map<Long, Order> ordersMap, List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Order order = ordersMap.get(orderItem.getOrderKey());
            order.getOrderItems().add(orderItem);
        }
    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }
}
