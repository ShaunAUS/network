package com.example.network.chat.server.command;

import com.example.network.chat.server.Session;

import java.io.IOException;

public interface CommandManager {
    void execute(String totalMessage, Session session) throws IOException;
}
