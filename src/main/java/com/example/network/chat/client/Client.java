package com.example.network.chat.client;

import static com.example.network.tcp.SocketCloseUtil.closeAll;
import static com.example.util.MyLogger.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        log("클라이언트 시작");
        socket = new Socket(host, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        readHandler = new ReadHandler(input, this);
        writeHandler = new WriteHandler(output, this);
        Thread readThread = new Thread(readHandler);
        Thread writeThread = new Thread(writeHandler);
        readThread.start();
        writeThread.start();

    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        readHandler.close();
        writeHandler.close();
        closeAll(socket, input, output);

        closed = true;
        log("연결종료 " + socket);

    }
}
