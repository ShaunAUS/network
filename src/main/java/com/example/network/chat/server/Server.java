package com.example.network.chat.server;

import com.example.network.chat.server.command.CommandManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.util.MyLogger.log;


public class Server {
    private final int port;
    private final CommandManager commandManager;
    private final SessionManager sessionManager;

    private ServerSocket serverSocket;

    public Server(int port, CommandManager commandManager, SessionManager sessionManager) {
        this.port = port;
        this.commandManager = commandManager;
        this.sessionManager = sessionManager;
    }

    public void start() throws IOException {
        log("서버 시작: " + commandManager.getClass());
        serverSocket = new ServerSocket(port);
        log("서버 소켓 시작 - 리스닝 포트: " + port);
        addShutdownHook();
        running();
    }

    private void running() {
        try {
            while (true) {
                Socket socket = serverSocket.accept(); // 블로킹
                log("소켓 연결: " + socket);

                Session session = new Session(socket, sessionManager, commandManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            log("서버  종료: " + e);
        }
    }

    private void addShutdownHook() {
        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));
    }

    static class ShutdownHook implements Runnable {
        private final ServerSocket serverSocket;
        private final SessionManager sessionManager;

        public ShutdownHook(ServerSocket serverSocket, SessionManager sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            try {
                //전체 세션(클라 담당자) 종료
                sessionManager.closeAll();
                serverSocket.close();

                // 그냥 종료시켜버리면 셧다운 훅이 안끝났는데 (자원정리 다 안됐는데) 종료되어버릴 수 있음
                //셧다운 훅의 실행이 끝날 때 까지는 기다려준다, 자원정리보장
                Thread.sleep(1000); // 자원 정리 대기
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }

        }
    }


}
