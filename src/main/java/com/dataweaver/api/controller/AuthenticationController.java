package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IAuthenticationController;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.model.beans.TokenBean;
import com.dataweaver.api.model.dtos.UserDTO;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.model.records.AuthenticationRecord;
import com.dataweaver.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RequiredArgsConstructor
@RestController
public class AuthenticationController implements IAuthenticationController, Serializable {

    private final AuthenticationService service;

    @Override
    public TokenBean login(AuthenticationRecord authenticationRecord) {
        return service.login(authenticationRecord);
    }

    @Override
    public TokenBean refreshToken(TokenBean tokenBeanRequest) {
        return service.refreshToken(tokenBeanRequest);
    }

    @Override
    public UserDTO updateSessionUser(UserDTO user) {
        return Converter.convertEntityToDTO(service.updateSessionUser(Converter.convertDTOToEntity(user, User.class)), UserDTO.class);
    }

    @Override
    public TokenBean getSession() {
        return service.getSession();
    }

}
