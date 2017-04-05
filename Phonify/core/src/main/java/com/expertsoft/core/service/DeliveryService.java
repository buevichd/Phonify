package com.expertsoft.core.service;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public interface DeliveryService {
    @NotNull BigDecimal getDeliveryPrice();
}
