package com.example.network.chat.server;

import com.example.network.chat.server.command.CommandManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.network.tcp.SocketCloseUtil.closeAll;
import static com.example.util.MyLogger.log;


public class Session implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private final CommandManager commandManager;

    private boolean closed = false;
    private String userName;

    public Session(Socket socket, SessionManager sessionManager, CommandManager commandManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.commandManager = commandManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                //클라 -> 서버에서 메세지 받음
                String received = input.readUTF();
                log("client -> server: " + received);

                // 서버는 메시지를 전체에게 보내야함
                commandManager.execute(received,this);


            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            sessionManager.sendAll(userName + "님이 퇴장하셨습니다.");
            close();

        }

    }

    //session 에게 메시지보내고 -> 클라에게 전달
    public void send(String message) throws IOException {
        log("server -> client: " + message);
        output.writeUTF(message);
    }

    public void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료" + socket);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
