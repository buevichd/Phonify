package com.expertsoft.core.entity;

import java.math.BigDecimal;

public class Phone {

    private Long key;
    private String model;
    private BigDecimal price;

    public Phone() {}

    public Phone(String model, BigDecimal price) {
        this.model = model;
        this.price = price;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;
        return key.equals(((Phone)obj).key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
