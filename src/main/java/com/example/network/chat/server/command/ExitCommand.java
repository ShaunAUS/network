package com.example.network.chat.server.command;

import com.example.network.chat.server.Session;
import com.example.network.chat.server.SessionManager;

import java.io.IOException;
import java.util.List;

public class ExitCommand implements Command {

    @Override
    public void execute(String[] args, Session session) throws IOException {
        throw new IOException("exit");
    }
}
