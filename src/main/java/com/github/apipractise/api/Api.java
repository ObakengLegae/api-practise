package com.github.apipractise.api;

import io.javalin.Javalin;

public class Api {
    private static final int DEFAULT_PORT = 7000;
    private final Javalin server;

    public Api() {
        this.server = Javalin.create();
    }

    public Javalin start(int port) {
        return this.server.start(port);
    }

    public Javalin stop() {
        return this.server.stop();
    }

    public static void main(String[] args) {
        Api api = new Api();
        api.start(DEFAULT_PORT);
    }
}
