package com.example.network.chat.server.command;

import com.example.network.chat.server.Session;
import com.example.network.chat.server.SessionManager;

import java.io.IOException;
import java.util.List;

public class UsersCommand implements Command {
    private final SessionManager sessionManager;

    public UsersCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args, Session session) throws IOException {
        List<String> usernames = sessionManager.getAllUsername();
        StringBuilder sb = new StringBuilder();
        sb.append("현재 접속자 목록 : ").append(usernames.size()).append("명\n");
        for (String username : usernames) {
            sb.append(" - ").append(username).append("\n");
        }
        //요청한 유저에게만 보이도록
        session.send(sb.toString());

    }
}
