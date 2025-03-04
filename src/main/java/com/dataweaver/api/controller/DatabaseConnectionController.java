package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IDatabaseConnectionController;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.model.dtos.DatabaseConnectionDTO;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.service.DatabaseConnectionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseConnectionController extends AbstractAllGetController<DatabaseConnectionService, DatabaseConnectionDTO> implements IDatabaseConnectionController {

    private final DatabaseConnectionService databaseConnectionService;

    DatabaseConnectionController(DatabaseConnectionService databaseConnectionService) {
        super(databaseConnectionService, new DatabaseConnectionDTO());
        this.databaseConnectionService = databaseConnectionService;
    }

    @Override
    public DatabaseConnectionDTO create(DatabaseConnectionDTO databaseConnection) {
        return Converter.convertEntityToDTO(databaseConnectionService.create(Converter.convertDTOToEntity(databaseConnection, DatabaseConnection.class)), DatabaseConnectionDTO.class);
    }

    @Override
    public DatabaseConnectionDTO edit(DatabaseConnectionDTO databaseConnection) {
        return Converter.convertEntityToDTO(databaseConnectionService.edit(Converter.convertDTOToEntity(databaseConnection, DatabaseConnection.class)), DatabaseConnectionDTO.class);
    }

}
