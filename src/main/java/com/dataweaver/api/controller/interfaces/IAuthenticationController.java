package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.beans.TokenBean;
import com.dataweaver.api.model.dtos.UserDTO;
import com.dataweaver.api.model.records.AuthenticationRecord;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IAuthenticationController.PATH)
public interface IAuthenticationController {
    String PATH = prefixPath + "/session";

    @PostMapping("/login")
    TokenBean login(@RequestBody @Valid AuthenticationRecord authenticationRecord);

    @PostMapping("/refresh-token")
    TokenBean refreshToken(@RequestBody TokenBean tokenBeanRequest);

    @PutMapping("/user")
    UserDTO updateSessionUser(@RequestBody UserDTO user);

    @GetMapping
    TokenBean getSession();

}

