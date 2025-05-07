package com.dataweaver.api.events.mail;

import com.dataweaver.api.infrastructure.mail.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailEventListener implements ApplicationListener<MailEvent> {

    private final MailSenderService mailSenderService;

    @Async
    @Override
    public void onApplicationEvent(MailEvent mailEvent) {
        mailSenderService.send(mailEvent.getTo(), mailEvent.getSubject(), mailEvent.getHtml());
    }

}