package com.github.apipractise.database.dbobjects;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record OrderResponse(
        int id,
        String customerEmail,
        String status,
        BigDecimal totalAmount,
        Timestamp createdAt
) {}
