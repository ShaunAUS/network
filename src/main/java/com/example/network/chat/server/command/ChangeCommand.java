package com.example.network.chat.server.command;

import com.example.network.chat.server.Session;
import com.example.network.chat.server.SessionManager;

import java.io.IOException;

public class ChangeCommand implements Command{
    private final SessionManager sessionManager;

    public ChangeCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args, Session session) throws IOException {
        String changeName = args[1];
        sessionManager.sendAll(session.getUserName() + "님이 " + changeName + "으로 변경하셨습니다.");
        session.setUserName(changeName);
    }
}
