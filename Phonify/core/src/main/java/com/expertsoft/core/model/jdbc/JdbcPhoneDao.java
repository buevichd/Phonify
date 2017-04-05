package com.expertsoft.core.model.jdbc;

import com.expertsoft.core.entity.Phone;
import com.expertsoft.core.model.interfaces.PhoneDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcPhoneDao extends NamedParameterJdbcDaoSupport implements PhoneDao {

    private static final String GET_PHONE_BY_ID = "SELECT * FROM phonify_phone WHERE phonify_phone.key = :key";
    private static final String UPDATE_PHONE = "UPDATE phonify_phone SET model=:model, price=:price WHERE phonify_phone.key=:key";
    private static final String INSERT_PHONE = "INSERT INTO phonify_phone (model, price) VALUES (:model, :price)";
    private static final String GET_ALL_PHONES = "SELECT * FROM phonify_phone";
    private static final String KEY = "key";
    private static final String MODEL = "model";
    private static final String PRICE = "price";

    @NotNull
    @Override
    public Phone get(Long key) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(KEY, key);
        return getNamedParameterJdbcTemplate().queryForObject(GET_PHONE_BY_ID, map, (resultSet, i) -> {
            Phone phone = new Phone();
            phone.setKey(resultSet.getLong(KEY));
            phone.setModel(resultSet.getString(MODEL));
            phone.setPrice(resultSet.getBigDecimal(PRICE));
            return phone;
        });
    }

    @Override
    public void update(@NotNull Phone phone) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(KEY, phone.getKey());
        map.put(MODEL, phone.getModel());
        map.put(PRICE, phone.getPrice());
        getNamedParameterJdbcTemplate().update(UPDATE_PHONE, map);
    }

    @Override
    public void create(@NotNull Phone phone) {
        Map<String, Object> map = new HashMap<>(2);
        map.put(MODEL, phone.getModel());
        map.put(PRICE, phone.getPrice());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(INSERT_PHONE, sqlParameterSource, keyHolder);
        phone.setKey(keyHolder.getKey().longValue());
    }

    @NotNull
    @Override
    public List<Phone> findAll() {
        List<Phone> allPhones = new ArrayList<>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(GET_ALL_PHONES);
        for (Map<String, Object> row : rows) {
            Phone phone = new Phone();
            phone.setKey((Long) row.get(KEY));
            phone.setModel((String) row.get(MODEL));
            phone.setPrice((BigDecimal) row.get(PRICE));
            allPhones.add(phone);
        }
        return allPhones;
    }

}
