package com.github.apipractise.database.dbobjects;

import java.math.BigDecimal;

public record CreateOrder(
        String customerEmail,
        String status,
        BigDecimal totalAmount
) {}
