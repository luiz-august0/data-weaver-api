package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.dtos.DatabaseConnectionDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IDatabaseConnectionController.PATH)
public interface IDatabaseConnectionController extends IAbstractAllGetController<DatabaseConnectionDTO> {

    String PATH = prefixPath + "/database";

    @PostMapping
    DatabaseConnectionDTO create(@RequestBody DatabaseConnectionDTO databaseConnection);

    @PutMapping
    DatabaseConnectionDTO edit(@RequestBody DatabaseConnectionDTO databaseConnection);

}