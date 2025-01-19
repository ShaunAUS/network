package com.example.network.chat.server.command;

import com.example.network.chat.server.Session;
import com.example.network.chat.server.SessionManager;

import java.io.IOException;

public class JoinCommand implements Command{
    private final SessionManager sessionManager;

    public JoinCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args, Session session) throws IOException {
        String userName = args[1];
        session.setUserName(userName);
        sessionManager.sendAll(userName + "님이 입장하셨습니다.");

    }
}
