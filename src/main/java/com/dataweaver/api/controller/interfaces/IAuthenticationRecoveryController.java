package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.records.AuthenticationRecoveryPasswordRecord;
import com.dataweaver.api.model.records.AuthenticationRecoveryRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(IAuthenticationRecoveryController.PATH)
public interface IAuthenticationRecoveryController {

    String PATH = IAuthenticationController.PATH + "/recovery";


    @PostMapping
    void generateRecovery(@RequestBody AuthenticationRecoveryRecord authenticationRecoveryRecord);

    @PostMapping("/password")
    void changePassword(@RequestBody AuthenticationRecoveryPasswordRecord authenticationRecoveryPasswordRecord);

}