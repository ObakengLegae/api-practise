package com.github.apipractise.database;

import com.github.apipractise.database.dbobjects.ProductResponse;

import java.math.BigDecimal;
import java.sql.*;

public class DatabaseSchema {

    public DatabaseSchema() {}

    public static void createSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {

            String productsSql = """
                    CREATE TABLE IF NOT EXISTS products (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(200) NOT NULL,
                        description TEXT,
                        price DECIMAL(10,2) NOT NULL,
                        stock_quantity INT DEFAULT 0,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
                    """;

            String ordersSql = """
                    CREATE TABLE IF NOT EXISTS orders (
                        id SERIAL PRIMARY KEY,
                        customer_email VARCHAR(150) NOT NULL,
                        status VARCHAR(50) DEFAULT 'pending',
                        total_amount DECIMAL(10, 2) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                    """;

            String orderItemsSql = """
                    CREATE TABLE IF NOT EXISTS order_items (
                        id SERIAL PRIMARY KEY,
                        order_id INT NOT NULL,
                        product_id INT NOT NULL,
                        quantity INT NOT NULL,
                        price_at_purchase DECIMAL(10, 2) NOT NULL,
                        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                        FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
                    );
                    """;

            stmt.execute(productsSql);
            stmt.execute(ordersSql);
            stmt.execute(orderItemsSql);
        }
    }

    public ProductResponse getProduct(Connection connection, int id) throws SQLException{
        ProductResponse productResponse = null;
        String sql = "SELECT * FROM products WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                int stockQuantity = rs.getInt("stock_quantity");

                productResponse = new ProductResponse(productId, name, description, price, stockQuantity);
            }
        }
        return productResponse;
    }
}
