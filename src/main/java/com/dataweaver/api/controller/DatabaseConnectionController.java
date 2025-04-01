package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IDatabaseConnectionController;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.model.dtos.DatabaseConnectionDTO;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.service.DatabaseConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DatabaseConnectionController implements IDatabaseConnectionController {

    private final DatabaseConnectionService databaseConnectionService;

    @Override
    public DatabaseConnectionDTO create(DatabaseConnectionDTO databaseConnection) {
        return Converter.convertEntityToDTO(databaseConnectionService.create(Converter.convertDTOToEntity(databaseConnection, DatabaseConnection.class)), DatabaseConnectionDTO.class);
    }

    @Override
    public DatabaseConnectionDTO edit(DatabaseConnectionDTO databaseConnection) {
        return Converter.convertEntityToDTO(databaseConnectionService.edit(Converter.convertDTOToEntity(databaseConnection, DatabaseConnection.class)), DatabaseConnectionDTO.class);
    }

    @Override
    public DatabaseConnectionDTO get() {
        return Converter.convertEntityToDTO(databaseConnectionService.get().orElse(null), DatabaseConnectionDTO.class);
    }

}
