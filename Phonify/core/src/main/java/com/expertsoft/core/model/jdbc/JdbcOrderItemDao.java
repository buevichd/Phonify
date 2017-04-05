package com.expertsoft.core.model.jdbc;

import com.expertsoft.core.entity.OrderItem;
import com.expertsoft.core.model.interfaces.OrderItemDao;
import com.expertsoft.core.model.interfaces.PhoneDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcOrderItemDao extends NamedParameterJdbcDaoSupport implements OrderItemDao {

    private static final String GET_ORDER_ITEM_BY_KEY = "SELECT * FROM phonify_order_item WHERE phonify_order_item.key = :key";
    private static final String GET_ORDER_ITEM_BY_ORDER_KEY = "SELECT * FROM phonify_order_item WHERE order_id = :order_id";
    private static final String UPDATE_ORDER_ITEM = "UPDATE phonify_order_item SET phone_id=:phone_id, quantity=:quantity, order_id=:order_id WHERE phonify_order_item.key=:key";
    private static final String INSERT_ORDER_ITEM = "INSERT INTO phonify_order_item (phone_id, quantity, order_id) VALUES (:phone_id, :quantity, :order_id);";
    private static final String GET_ALL_ORDER_ITEMS = "SELECT * FROM phonify_order_item";

    private static final String KEY = "key";
    private static final String PHONE_ID = "phone_id";
    private static final String QUANTITY = "quantity";
    private static final String ORDER_ID = "order_id";

    private PhoneDao phoneDao;

    @NotNull
    @Override
    public OrderItem get(Long key) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(KEY, key);
        return getNamedParameterJdbcTemplate().queryForObject(GET_ORDER_ITEM_BY_KEY, map, (resultSet, i) -> {
           OrderItem orderItem = new OrderItem();
           orderItem.setKey(resultSet.getLong(KEY));
           orderItem.setPhone(phoneDao.get(resultSet.getLong(PHONE_ID)));
           orderItem.setQuantity(resultSet.getLong(QUANTITY));
           orderItem.setOrderKey(resultSet.getLong(ORDER_ID));
           return orderItem;
        });
    }

    @Override
    public @NotNull List<OrderItem> getOrderItemsByOrderKey(@NotNull Long orderKey) {
        List<OrderItem> orderItems = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(1);
        map.put(ORDER_ID, orderKey);
        List<Map<String, Object>> rows = getNamedParameterJdbcTemplate().queryForList(GET_ORDER_ITEM_BY_ORDER_KEY, map);
        for (Map<String, Object> row : rows) {
            orderItems.add(getOrderItemFromRow(row));
        }
        return orderItems;
    }

    @Override
    public void create(@NotNull OrderItem orderItem) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(PHONE_ID, orderItem.getPhone().getKey());
        map.put(QUANTITY, orderItem.getQuantity());
        map.put(ORDER_ID, orderItem.getOrderKey());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(INSERT_ORDER_ITEM, sqlParameterSource, keyHolder);
        orderItem.setKey(keyHolder.getKey().longValue());
    }

    @Override
    public void update(@NotNull OrderItem orderItem) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(KEY, orderItem.getKey());
        map.put(PHONE_ID, orderItem.getPhone().getKey());
        map.put(QUANTITY, orderItem.getQuantity());
        map.put(ORDER_ID, orderItem.getOrderKey());
        getNamedParameterJdbcTemplate().update(UPDATE_ORDER_ITEM, map);
    }

    @NotNull
    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> allOrderItems = new ArrayList<>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(GET_ALL_ORDER_ITEMS);
        for (Map<String, Object> row : rows) {
            allOrderItems.add(getOrderItemFromRow(row));
        }
        return allOrderItems;
    }

    @NotNull
    private OrderItem getOrderItemFromRow(Map<String, Object> row) {
        OrderItem orderItem = new OrderItem();
        orderItem.setKey((Long) row.get(KEY));
        orderItem.setPhone(phoneDao.get((Long) row.get(PHONE_ID)));
        orderItem.setQuantity((Long) row.get(QUANTITY));
        orderItem.setOrderKey((Long) row.get(ORDER_ID));
        return orderItem;
    }

    public PhoneDao getPhoneDao() {
        return phoneDao;
    }

    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }
}
