package com.dataweaver.api.events.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(String to, String subject, String html) {
        MailEvent mailEvent = new MailEvent(this, to, subject, html);
        applicationEventPublisher.publishEvent(mailEvent);
    }

}