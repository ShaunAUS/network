package com.example.network.tcp.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.util.MyLogger.log;

public class SessionV3 implements Runnable {

    private final Socket socket;

    public SessionV3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            while (true) {
                //클라로부터 문자 받기
                String received = input.readUTF();
                log("client -> server: " + received);

                if (received.equals("quit")) {
                    break;
                }

                //클라로 문자 보내기
                String toSend = received + "World";
                output.writeUTF(toSend);
                log("client <- server: " + toSend);
            }

            log("연결종료" + socket);
            input.close();
            output.close();
            socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
