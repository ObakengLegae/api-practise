package com.github.apipractise.api;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {
    private static final int TESTING_PORT = 8000;

    @Test
    void shouldGetOKResponse() {
        Api api = new Api();
        api.start(TESTING_PORT);
        HttpResponse<String> response = Unirest.get("http://localhost:8000/health").asString();
        assertEquals(200, response.getStatus());
        api.stop();
    }

}