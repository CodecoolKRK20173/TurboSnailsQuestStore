package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;


public class StudentCrowdfundingController implements HttpHandler {
    private DBConnector connectionPool;
    private SessionDAO sessionDAO;
    private Integer userId;
    private Integer student_level;
    private LevelsDAO levelsDAO;


    public StudentCrowdfundingController(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionResolver sessionResolver = new SessionResolver(httpExchange, connectionPool);
        sessionResolver.checkIfSessionIsValid();
        String response = "";
        String method = httpExchange.getRequestMethod();

        // Probably should be in view
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/crowdfunding.twig");
        JtwigModel model = JtwigModel.newModel();

        String cookieString = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (cookieString != null) {
            HttpCookie cookie = HttpCookie.parse(cookieString).get(0);
            String sesionNumber = cookie.getValue();
            sessionDAO = new SessionDAOImpl(connectionPool);
            userId = sessionDAO.getUserIdBySession( sesionNumber );

            levelsDAO = new LevelsDAOImpl(connectionPool);
            System.out.println(userId);
            student_level = levelsDAO.getStudentLevel(userId);
            System.out.println(student_level);
        }else {
            System.out.println("cookie is null");
        }

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){

            model.with("student_level", student_level);
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){

            model.with("student_level", student_level);
            response = template.render(model);
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}