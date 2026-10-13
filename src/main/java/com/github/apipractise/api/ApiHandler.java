package com.github.apipractise.api;

import com.github.apipractise.database.DatabaseConnection;
import com.github.apipractise.database.DatabaseSchema;

import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.SQLException;

public class ApiHandler {
    private static Connection connection;
    private static final String CONTENT_TYPE = "application/json";

    private ApiHandler() {}

    public static void initializeConnection(Connection connection) throws SQLException {
        ApiHandler.connection = connection;
        DatabaseSchema.createSchema(connection);
    }

    public static void getHealthCheck(Context ctx) {
        if (!DatabaseSchema.healthCheck(connection)) {
            response(ctx, 500, "An internal error occurred.");
        }
        response(ctx, 200, "OK");
    }

    private static void response (Context ctx, int status, String message) {
        ctx.status(status).contentType(CONTENT_TYPE).result("{\"message\":\"" + message + "\"}");
    }
}
