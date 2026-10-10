package com.github.apipractise.api;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiTest {
    private static final int TESTING_PORT = 8000;

    @Test
    public void shouldGetResponse() {
        Api api = new Api();
        api.start(TESTING_PORT);
        HttpResponse<String> response = Unirest.get("http://localhost:8000/hello").asString();
        assertEquals(200, response.getStatus());
        assertEquals("Api is active", response.getBody());
        api.stop();
    }

}