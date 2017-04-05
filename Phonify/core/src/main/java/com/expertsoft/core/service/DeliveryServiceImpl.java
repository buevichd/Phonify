package com.expertsoft.core.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("deliveryService")
public class DeliveryServiceImpl implements DeliveryService {

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @NotNull
    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

}
