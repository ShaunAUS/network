package com.example.was.v8;


import com.example.was.httpserver.reqres.HttpRequest;
import com.example.was.httpserver.reqres.HttpResponse;
import com.example.was.httpserver.servlet.annotation.Mapping;

public class SearchControllerV8 {

    @Mapping("/search")
    public void search(HttpRequest request, HttpResponse response) {
        String query = request.getParameter("q");
        response.writeBody("<h1>Search</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li>query: " + query + "</li>");
        response.writeBody("</ul>");
    }
}
