package com.example.network.tcp.v6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.network.tcp.SocketCloseUtil.closeAll;
import static com.example.util.MyLogger.log;


public class SessionV6 implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManagerV6 sessionManager;
    private boolean closed = false;

    public SessionV6(Socket socket, SessionManagerV6 sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 클라이언트로부터 문자 받기
                // 여기서 대기하다가 서버종료가 일어나면 익셉션 터짐
                String received = input.readUTF();
                log("client -> server: " + received);

                if (received.equals("exit")) {
                    break;
                }

                // 클라이언트에게 문자 보내기
                String toSend = received + " World!";
                output.writeUTF(toSend);
                log("client <- server: " + toSend);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    // 클라가 종료시, 서버 종료시 동시에 호출될 수 있다. 그래서 synchronized
    //이건 try 구문이 끝나서가아닌 다른 누군가에 의한 종료
    // 클라가 종료하건, 서버가 종료하더 이걸로 통일, autoCloseable 아님
    public synchronized void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output); // shutdown
        closed = true;
        log("연결 종료: " + socket);
    }
}
