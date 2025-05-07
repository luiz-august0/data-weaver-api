package com.dataweaver.api.events.user;

import com.dataweaver.api.infrastructure.context.TenantContext;
import com.dataweaver.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener implements ApplicationListener<UserEvent> {

    private final UserRepository userRepository;

    @Async
    @Override
    public void onApplicationEvent(UserEvent userEvent) {
        TenantContext.setCurrentTenant(userEvent.getSchema());

        userRepository.save(userEvent.getUser());
    }

}