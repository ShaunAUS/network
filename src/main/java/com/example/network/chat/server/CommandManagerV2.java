
package com.example.network.chat.server;

import com.example.network.chat.server.command.CommandManager;

import java.io.IOException;
import java.util.List;

public class CommandManagerV2 implements CommandManager {

    private final SessionManager sessionManager;
    private static final String DELIMITER = "\\|";

    public CommandManagerV2(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    //사용자로부터 명령 받고 실행
    @Override
    public void execute(String totalMessage, Session session) throws IOException {

        if (totalMessage.startsWith("/exit")) {
            throw new IOException("exit");
        }

        if (totalMessage.startsWith("/users")) {
            List<String> usernames = sessionManager.getAllUsername();
            StringBuilder sb = new StringBuilder();
            sb.append("현재 접속자 목록 : ").append(usernames.size()).append("명\n");
            for (String username : usernames) {
                sb.append(" - ").append(username).append("\n");
            }

            //요청한 유저에게만 보이도록
            session.send(sb.toString());
        }


        // 이름 변경할때-> /change|{userName}
        if (totalMessage.startsWith("/change")) {
            String[] split = totalMessage.split(DELIMITER);
            String changeName = split[1];
            sessionManager.sendAll(session.getUserName() + "님이 " + changeName + "으로 변경하셨습니다.");
            session.setUserName(changeName);
        }


        // 메세지 보낼때-> /message|{message}
        if (totalMessage.startsWith("/message")) {
            String[] split = totalMessage.split(DELIMITER);
            String message = split[1];
            sessionManager.sendAll("[" + session.getUserName() + " ] " + message);

        }

        // 입장할때 ->  /join|{userName}
        if (totalMessage.startsWith("/join")) {
            String[] split = totalMessage.split(DELIMITER);
            String userName = split[1];
            session.setUserName(userName);
            sessionManager.sendAll(userName + "님이 입장하셨습니다.");
        }


        if (totalMessage.startsWith("/exit")) {
            throw new IOException("exit");
        }

    }
}
