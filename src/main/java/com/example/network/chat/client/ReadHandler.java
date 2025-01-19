package com.example.network.chat.client;

import java.io.DataInputStream;
import java.io.IOException;

import static com.example.util.MyLogger.log;


//서버 -> 클라 메시지 송신
public class ReadHandler implements Runnable {

    private final DataInputStream input;
    private final Client client;
    public boolean closed = false;

    public ReadHandler(DataInputStream input, Client client) {
        this.input = input;
        this.client = client;
    }

    @Override
    public void run() {

        try {
            while (true) {
                String received = input.readUTF();
                System.out.println(received);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            client.close();
        }
    }


    public synchronized void close() {
        if (closed) {
            return;
        }
        closed = true;
        log("readHandler 종료");

    }
}
