package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.DBConnector;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;


public class MentorCodecoolersController implements HttpHandler {
    DBConnector connectionPool;

    public MentorCodecoolersController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
