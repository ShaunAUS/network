package com.example.network.chat.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.example.util.MyLogger.log;

//클라 -> 서버로 메시지 송신
public class WriteHandler implements Runnable {

    private static final String DELIMITER = "|";
    public boolean closed = false;

    private final DataOutputStream output;
    private final Client client;

    public WriteHandler(DataOutputStream output, Client client) {
        this.output = output;
        this.client = client;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            String username = inputUserName(scanner);
            //입장했을때
            output.writeUTF("/join" + DELIMITER + username);

            while (true) {
                String toSend = scanner.nextLine(); //블로킹, 블로킹중에 종료되면 NoSuchElementException 발생

                // 대화내용 아무것도  안쓰면 계속 반복
                if (toSend.isEmpty()) {
                    continue;
                }

                if (toSend.equals("/exit")) {
                    output.writeUTF(toSend);
                    break;
                }

                if (toSend.startsWith("/")) {
                    output.writeUTF(toSend);
                } else {
                    output.writeUTF("/message" + DELIMITER + toSend);
                }

            }
        } catch (IOException | NoSuchElementException e) {
            log(e);
        } finally {
            client.close();
        }

    }

    private static String inputUserName(Scanner scanner) {
        System.out.println("대화를 시작합니다.");
        System.out.println("이름을 입력하세요.");
        String username;

        do {
            username = scanner.nextLine();
        } while (username.isEmpty());
        return username;
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        try {
            //scanner.nextLine(); 여기서 블로킹되서 이걸 종료하기위한것
            // Scanner 입력중지
            System.in.close();
        } catch (IOException e) {
            log(e);
        }
        closed = true;
        log("writeHandler 종료");
    }
}
