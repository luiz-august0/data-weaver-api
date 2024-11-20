package com.dataweaver.api.events.mail;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MailEvent extends ApplicationEvent {

    private final String to;

    private final String subject;

    private final String html;

    public MailEvent(Object source, String to, String subject, String html) {
        super(source);
        this.to = to;
        this.subject = subject;
        this.html = html;
    }

}