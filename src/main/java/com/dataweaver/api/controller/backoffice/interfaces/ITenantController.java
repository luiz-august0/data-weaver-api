package com.dataweaver.api.controller.backoffice.interfaces;

import com.dataweaver.api.model.dtos.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dataweaver.api.constants.Paths.backofficePrefixPath;

@RequestMapping(ITenantController.PATH)
public interface ITenantController {

    String PATH = backofficePrefixPath + "/tenant";

    @PostMapping("/create")
    void create(@RequestBody UserDTO user);

}