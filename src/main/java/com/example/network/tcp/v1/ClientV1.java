package com.example.network.tcp.v1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.util.MyLogger.log;

public class ClientV1 {
    private static final int PORT = 12345;


    public static void main(String[] args) throws IOException {

       log("ClientV1 시작");
        Socket socket = new Socket("localhost", PORT); //TCP/Ip 연결, port는 서버포트, Localport는 컴퓨터에서 남는 포트 아무거나지정해서 줌

        socket.getOutputStream();
        socket.getInputStream();

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓연결 " + socket);


        //서버에게 문자 보내기
        String toSend = "Hello";
        output.writeUTF(toSend);
        log("client -> server: " + toSend);

        //서버로부터 문자 받기
        String received = input.readUTF();
        log("client <- server: " + received);

        //자원정리
        log("연결종료");
        input.close();
        output.close();
        socket.close();


    }
}
