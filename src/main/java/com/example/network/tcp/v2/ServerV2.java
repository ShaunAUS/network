package com.example.network.tcp.v2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.util.MyLogger.log;

public class ServerV2 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {

        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트 :" + PORT);

        // 여기서 들어오는놈 감지
        //여기서 연결이되서 socket객체가 만들어지면 os에 있는 backlog queue에서 제거됨`
        Socket socket = serverSocket.accept();
        log("소켓 연결됨 " + socket);
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


        //자원정리
        //순서는 역순으로!!
        log("연결종료" + socket);
        input.close();
        output.close();
        socket.close();
        serverSocket.close();


    }
}
