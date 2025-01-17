package com.example.network.tcp.v6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.util.MyLogger.log;


// 자원정리 하는시점 1.클라가 종료할때, 2.서버가 종료될때
//`try-with-resources` 는 try 선언부에서 사용한 자원을 try가 끝나는 시점에 정리한다. 따라서 try에서 자원의 선언과 자원 정리를 묶어서 처리할 때 사용할 수 있다
// 서버를 종료하는 시점에도 `Session` 의 자원을 정리해야 하기 때문에 try-with-resources 를 사용할 수 없다.
public class ServerV6 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        SessionManagerV6 sessionManager = new SessionManagerV6();
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        // ShutdownHook 등록
        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));

        try {
            while (true) {
                Socket socket = serverSocket.accept(); // 블로킹, 여기서 shutdownHook이 실행되어 종료되면 예외터짐
                log("소켓 연결: " + socket);

                //스레드
                SessionV6 session = new SessionV6(socket, sessionManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            // serverSocket.close() 에서 블로킹하고 있던 메인스레드가 showdownHook이 실행되어 종료될때 터짐
            log("서버 소켓 종료: " + e);
        }

    }

    //서버에서 종료 할때
    static class ShutdownHook implements Runnable {
        private final ServerSocket serverSocket;
        private final SessionManagerV6 sessionManager;

        public ShutdownHook(ServerSocket serverSocket, SessionManagerV6 sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            try {
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
