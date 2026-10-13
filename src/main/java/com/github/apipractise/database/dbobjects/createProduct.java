package com.github.apipractise.database.dbobjects;

import java.math.BigDecimal;

public record createProduct (
        String name,
        String description,
        BigDecimal price,
        int stockQuantity
) {}
