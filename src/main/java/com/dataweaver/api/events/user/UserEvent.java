package com.dataweaver.api.events.user;

import com.dataweaver.api.model.entities.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserEvent extends ApplicationEvent {

    private final User user;

    private final String schema;

    public UserEvent(Object source, User user, String schema) {
        super(source);
        this.user = user;
        this.schema = schema;
    }

}