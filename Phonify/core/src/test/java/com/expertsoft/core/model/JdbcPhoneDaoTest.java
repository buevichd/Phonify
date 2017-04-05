package com.expertsoft.core.model;

import com.expertsoft.core.entity.Phone;
import com.expertsoft.core.model.jdbc.JdbcPhoneDao;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config.xml")
@ActiveProfiles({"test", "default"})
@Transactional
public class JdbcPhoneDaoTest {

    @Autowired
    private JdbcPhoneDao jdbcPhoneDao;

    @Test
    public void shouldCreateAndGetPhone() {
        Phone createdPhone = new Phone("iPhone 5", new BigDecimal("100.00"));

        jdbcPhoneDao.create(createdPhone);
        Phone phone = jdbcPhoneDao.get(createdPhone.getKey());

        Assert.assertTrue(EqualsBuilder.reflectionEquals(createdPhone, phone));
    }

    @Test
    public void shouldUpdatePhone() {
        Phone createdPhone = new Phone("iPhone 5", new BigDecimal("100.00"));
        jdbcPhoneDao.create(createdPhone);
        createdPhone.setModel("Samsung");

        jdbcPhoneDao.update(createdPhone);
        Phone phone = jdbcPhoneDao.get(createdPhone.getKey());

        Assert.assertTrue(EqualsBuilder.reflectionEquals(createdPhone, phone));
    }

    @Test
    public void shouldFindAllCreatedPhones() {
        Phone createdPhone = new Phone("iPhone 5", new BigDecimal("100.00"));
        jdbcPhoneDao.create(createdPhone);

        List<Phone> allPhones = jdbcPhoneDao.findAll();

        Assert.assertEquals(1, (long)allPhones.size());
    }

}
