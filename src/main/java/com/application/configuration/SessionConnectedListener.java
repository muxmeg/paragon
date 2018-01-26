package com.application.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class SessionConnectedListener implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate template;

    @Autowired
    public SessionConnectedListener(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        String sessionId = sessionDisconnectEvent.getSessionId();
        log.debug("Disconnected " + sessionId);
    }
}