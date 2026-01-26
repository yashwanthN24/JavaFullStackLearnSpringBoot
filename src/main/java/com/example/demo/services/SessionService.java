package com.example.demo.services;

import com.example.demo.entities.Session;
import com.example.demo.entities.User;
import com.example.demo.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final int SESSION_LIMIT = 2;

    public void generateNewSession(User user , String refreshToken){
//                first find all the active session of the user
        List<Session> userSessions = sessionRepository.findByUser(user);
        System.out.println();
        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);

    }

    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("Session Not Found For Refresh token : %s".formatted(refreshToken)));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void logoutSession(String refreshToken){
//        delete session having the refresh token this deltes only that specfic sessio as we already handling the cazse of mmultiple session in generateNewSession
        Session sessionToBeDeleted = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("No Session Found to logout"));
        sessionRepository.delete(sessionToBeDeleted);

    }

}
