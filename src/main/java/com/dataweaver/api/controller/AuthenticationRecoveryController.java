package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IAuthenticationRecoveryController;
import com.dataweaver.api.model.records.AuthenticationRecoveryPasswordRecord;
import com.dataweaver.api.model.records.AuthenticationRecoveryRecord;
import com.dataweaver.api.service.AuthenticationRecoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RequiredArgsConstructor
@RestController
public class AuthenticationRecoveryController implements IAuthenticationRecoveryController, Serializable {

    private final AuthenticationRecoveryService authenticationRecoveryService;

    @Override
    public void generateRecovery(AuthenticationRecoveryRecord authenticationRecoveryRecord) {
        authenticationRecoveryService.generateRecovery(authenticationRecoveryRecord);
    }

    @Override
    public void changePassword(AuthenticationRecoveryPasswordRecord authenticationRecoveryPasswordRecord) {
        authenticationRecoveryService.changePassword(authenticationRecoveryPasswordRecord);
    }

}
