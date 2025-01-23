package com.example.was.v6;

import com.example.was.httpserver.HttpServer;
import com.example.was.httpserver.ServletManager;
import com.example.was.httpserver.reflection.ReflectionServlet;
import com.example.was.httpserver.servlet.DiscardServlet;
import com.example.was.v5.servlet.HomeServlet;
import com.example.was.v6.controller.SearchControllerV6;
import com.example.was.v6.controller.SiteControllerV6;

import java.io.IOException;
import java.util.List;

public class ServerMainV6 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        List<Object> controllers = List.of(new SiteControllerV6(), new SearchControllerV6());
        ReflectionServlet reflectionServlet = new ReflectionServlet(controllers);

        ServletManager servletManager = new ServletManager();
        servletManager.setDefaultServlet(reflectionServlet);
        servletManager.add("/", new HomeServlet());
        servletManager.add("/favicon.ico", new DiscardServlet());

        HttpServer server = new HttpServer(PORT, servletManager);
        server.start();
    }
}
