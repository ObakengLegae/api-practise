package com.github.apipractise.api;

import com.github.apipractise.database.DatabaseConnection;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.SQLException;

public class Api {
    private static final int DEFAULT_PORT = 7000;
    private final Javalin server;
    private final Connection connection;

    public Api() {
        try {
            this.server = Javalin.create();

            this.connection = DatabaseConnection.connect();
            ApiHandler.initializeConnection(connection);

            this.server.get("/health", ApiHandler::getHealthCheck);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Javalin start(int port) {
        return this.server.start(port);
    }

    public Javalin stop() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
        return this.server.stop();
    }

    public static void main(String[] args) {
        Api api = new Api();
        api.start(DEFAULT_PORT);
    }
}
