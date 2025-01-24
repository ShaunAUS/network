package com.example.was.v8;


import com.example.was.httpserver.HttpServer;
import com.example.was.httpserver.ServletManager;
import com.example.was.httpserver.servlet.DiscardServlet;
import com.example.was.httpserver.servlet.HttpServlet;
import com.example.was.httpserver.servlet.annotation.AnnotationServletV2;
import com.example.was.httpserver.servlet.annotation.AnnotationServletV3;

import java.io.IOException;
import java.util.List;

public class ServerMainV8 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        List<Object> controllers = List.of(new SiteControllerV8(), new SearchControllerV8());

        // 컨트룰러 + 애노테이션 조합 여러개를 하나의 서블릿으로 묶고 서블릿매니저에 넣는다
        //HttpServlet annotationServlet = new AnnotationServletV2(controllers);
        HttpServlet annotationServlet = new AnnotationServletV3(controllers);

        ServletManager servletManager = new ServletManager();
        servletManager.setDefaultServlet(annotationServlet);
        servletManager.add("/favicon.ico", new DiscardServlet());

        //그렇게 완서된 서브릿매니저로 서버를 실행
        HttpServer server = new HttpServer(PORT, servletManager);
        server.start();
    }
}
