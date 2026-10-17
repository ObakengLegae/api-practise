package com.github.apipractise.database;

import com.github.apipractise.database.dbobjects.CreateOrder;
import com.github.apipractise.database.dbobjects.CreateProduct;
import com.github.apipractise.database.dbobjects.OrderResponse;
import com.github.apipractise.database.dbobjects.ProductResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseSchemaTest {
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DatabaseConnection.connect();
        DatabaseSchema.createSchema(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testProductColumns() throws SQLException{
        Map<String, String> schemaTable = new HashMap<>(Map.of(
                "id", "INTEGER",
                "name", "CHARACTER VARYING",
                "description", "TEXT",
                "price", "NUMERIC",
                "stock_quantity", "INTEGER",
                "created_at", "TIMESTAMP WITHOUT TIME ZONE"
        ));

        Map<String, String> dbTable = columns("products");
        assertEquals(schemaTable, dbTable);
    }

    @Test
    void testOrdersColumns() throws SQLException {
        Map<String, String> schemaTable = new HashMap<>(Map.of(
                "id", "INTEGER",
                "customer_email", "CHARACTER VARYING",
                "status", "CHARACTER VARYING",
                "total_amount", "NUMERIC",
                "created_at", "TIMESTAMP WITHOUT TIME ZONE"
        ));

        Map<String, String> dbTable = columns("orders");
        assertEquals(schemaTable, dbTable);
    }

    @Test
    void testOrderItemsTable() throws SQLException {
        Map<String, String> schemaTable = new HashMap<>(Map.of(
                "id", "INTEGER",
                "order_id", "INTEGER",
                "product_id", "INTEGER",
                "quantity", "INTEGER",
                "price_at_purchase", "NUMERIC"
        ));

        Map<String, String> dbTable = columns("order_items");
        assertEquals(schemaTable, dbTable);
    }

    @Test
    void testHealthCheck() {
        assertTrue(DatabaseSchema.healthCheck(connection));
    }

    @Test
    void testAddProduct() throws SQLException {
        CreateProduct createProduct = new CreateProduct(
                "pencil",
                "A writing or drawing tool",
                new BigDecimal("15.00"),
                1
        );

        ProductResponse response = DatabaseSchema.addProduct(connection, createProduct);

        assertEquals("pencil", response.name());
        assertEquals("A writing or drawing tool", response.description());
        assertEquals(new BigDecimal("15.00"), response.price());
        assertEquals(1, response.stockQuantity());
    }

    @Test
    void testAddOrder() throws SQLException {
        CreateOrder createOrder = new CreateOrder(
                "someone@gmail.com",
                "created",
                new BigDecimal("150.00")
        );

        OrderResponse response = DatabaseSchema.addOrder(connection, createOrder);

        assertEquals("someone@gmail.com", response.customerEmail());
        assertEquals("created", response.status());
        assertEquals(new BigDecimal("150.00"), response.totalAmount());
    }

    private Map<String, String> columns(String tableName) throws SQLException {
        Map<String, String> columns = new HashMap<>();
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery("""
                    SELECT column_name, data_type
                    FROM information_schema.columns
                    WHERE table_schema = 'public' 
                    AND table_name = '%s';
                    """.formatted(tableName))
        ) {
            while (resultSet.next()) {
                columns.put(resultSet.getString("column_name"), resultSet.getString("data_type").toUpperCase());
            }
        }
        return columns;
    }
}