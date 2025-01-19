package com.example.network.tcp.v6;

import java.util.ArrayList;
import java.util.List;

// 동시성 처리 synchronized
public class SessionManagerV6 {

    private List<SessionV6> sessions = new ArrayList<>();

    public synchronized void add(SessionV6 session) {
        sessions.add(session);
    }

    //클라에서 종료
    public synchronized void remove(SessionV6 session) {
        sessions.remove(session);
    }

    //서버에서 종료할때
    public synchronized void closeAll() {
        for (SessionV6 session : sessions) {
            session.close();
        }
        sessions.clear();
    }
}
