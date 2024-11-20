package com.dataweaver.api.controller.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IHealthCheckController.PATH)
public interface IHealthCheckController {

    String PATH = prefixPath + "/health";

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    String check();

}