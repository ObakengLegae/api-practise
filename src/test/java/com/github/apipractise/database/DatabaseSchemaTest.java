package com.github.apipractise.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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