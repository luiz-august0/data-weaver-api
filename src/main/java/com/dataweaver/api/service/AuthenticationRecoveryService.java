package com.dataweaver.api.service;

import com.dataweaver.api.events.mail.MailEvent;
import com.dataweaver.api.infrastructure.context.TenantContext;
import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.dataweaver.api.infrastructure.mail.builders.RecoveryMailBuilder;
import com.dataweaver.api.infrastructure.mail.interfaces.ITemplate;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.model.records.AuthenticationRecoveryPasswordRecord;
import com.dataweaver.api.model.records.AuthenticationRecoveryRecord;
import com.dataweaver.api.repository.TenantRepository;
import com.dataweaver.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationRecoveryService {

    private final TenantRepository tenantRepository;

    private final TokenService tokenService;

    private final ApplicationEventPublisher eventPublisher;

    private final RecoveryMailBuilder recoveryMailBuilder;

    private final UserService userService;

    public void generateRecovery(AuthenticationRecoveryRecord authenticationRecoveryRecord) {
        if (!tenantRepository.existsSchemaByName(Utils.nvl(authenticationRecoveryRecord.tenant(), ""))) {
            throw new ApplicationGenericsException(EnumGenericsException.INVALID_TENANT);
        }

        Optional<User> optionalUser = userService.findByLogin(Utils.nvl(authenticationRecoveryRecord.login(), ""));

        if (optionalUser.isEmpty()) {
            throw new ApplicationGenericsException(EnumGenericsException.USER_NOT_FOUND);
        }

        User user = optionalUser.get();

        TenantContext.setCurrentTenant(user.getSchema());

        String recoveryToken = tokenService.generateRecovery(user);

        ITemplate iTemplate = recoveryMailBuilder.build(recoveryToken);

        eventPublisher.publishEvent(new MailEvent(this, user.getLogin(), iTemplate.getSubject(), iTemplate.getHtml()));
    }

    public void changePassword(AuthenticationRecoveryPasswordRecord authenticationRecoveryPasswordRecord) {
        String subjectToken = tokenService.validateRecoveryToken(authenticationRecoveryPasswordRecord.token());

        String[] subject = new String(Base64.getDecoder().decode(subjectToken.getBytes())).split("-");

        Integer userId = Integer.parseInt(subject[0]);
        String schema = subject[1];

        TenantContext.setCurrentTenant(schema);

        User user = userService.findAndValidate(userId);

        user.setPassword(authenticationRecoveryPasswordRecord.password());

        userService.update(userId, user);
    }

}

