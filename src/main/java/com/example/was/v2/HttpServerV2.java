package com.example.was.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.util.MyLogger.log;


public class HttpServerV2 {

    //요청 두개 이상 받도록 멀티쓰레드
    private final ExecutorService es = Executors.newFixedThreadPool(10);
    private final int port;

    public HttpServerV2(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        log("서버 시작 port: " + port);

        while (true) {
            //요청들어오면 소켓으로 다른스레드에게 넘김
            Socket socket = serverSocket.accept();
            es.submit(new HttpRequestHandlerV2(socket));
        }
    }
}
