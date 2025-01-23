package com.example.was.v5;


import com.example.was.httpserver.ServletManager;
import com.example.was.httpserver.servlet.DiscardServlet;
import com.example.was.v5.servlet.HomeServlet;
import com.example.was.v5.servlet.SearchServlet;
import com.example.was.v5.servlet.Site1Servlet;
import com.example.was.v5.servlet.Site2Servlet;
import com.example.was.httpserver.HttpServer;


import java.io.IOException;

public class ServerMainV5 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServletManager servletManager = new ServletManager();
        servletManager.add("/", new HomeServlet());
        servletManager.add("/site1", new Site1Servlet());
        servletManager.add("/site2", new Site2Servlet());
        servletManager.add("/search", new SearchServlet());
        servletManager.add("/favicon.ico", new DiscardServlet());

        //url에 다른 서블릿 모아서 서버에 넘기고 실행
        HttpServer server = new HttpServer(PORT, servletManager);
        server.start();
    }
}
