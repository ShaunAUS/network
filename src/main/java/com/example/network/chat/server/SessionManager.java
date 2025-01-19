package com.example.network.chat.server;

import com.example.network.tcp.v6.SessionV6;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.MyLogger.log;

public class SessionManager {
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);
    private List<Session> sessions = new ArrayList<>();

    public synchronized void add(Session session) {
        sessions.add(session);
    }

    //클라에서 종료
    public synchronized void remove(Session session) {
        sessions.remove(session);
    }

    //서버에서 종료할때
    public synchronized void closeAll() {
        for (Session session : sessions) {
            session.close();
        }
        sessions.clear();
    }

    public synchronized void sendAll(String message) {
        for (Session session : sessions) {
            try {
                session.send(message);
            } catch (IOException e) {
                log(e);
            }
        }
    }

    public synchronized List<String> getAllUsername() {
        List<String> usernames = new ArrayList<>();
        for (Session session : sessions) {
            if (session.getUserName() != null) {
                usernames.add(session.getUserName());
            }
        }
        return usernames;
    }
}
