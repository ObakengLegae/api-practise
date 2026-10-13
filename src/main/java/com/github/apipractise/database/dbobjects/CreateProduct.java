package com.github.apipractise.database.dbobjects;

import java.math.BigDecimal;

public record CreateProduct(
        String name,
        String description,
        BigDecimal price,
        int stockQuantity
) {}
