package com.github.apipractise.database.dbobjects;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record ProductResponse (
    int id,
    String name,
    String description,
    BigDecimal price,
    int stockQuantity
) {}
