package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.dtos.DatabaseConnectionDTO;
import org.springframework.web.bind.annotation.*;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IDatabaseConnectionController.PATH)
public interface IDatabaseConnectionController {

    String PATH = prefixPath + "/database";

    @PostMapping
    DatabaseConnectionDTO create(@RequestBody DatabaseConnectionDTO databaseConnection);

    @PutMapping
    DatabaseConnectionDTO edit(@RequestBody DatabaseConnectionDTO databaseConnection);

    @GetMapping
    DatabaseConnectionDTO get();
}