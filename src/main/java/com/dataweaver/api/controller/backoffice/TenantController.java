package com.dataweaver.api.controller.backoffice;

import com.dataweaver.api.controller.backoffice.interfaces.ITenantController;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.model.dtos.UserDTO;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TenantController implements ITenantController {

    private final TenantService tenantService;

    @Override
    public void create(UserDTO user) {
        tenantService.createTenantAndUser(Converter.convertDTOToEntity(user, User.class));
    }

}
