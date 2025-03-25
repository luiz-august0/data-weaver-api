package com.dataweaver.api.validators;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.pattern.validators.AbstractValidator;
import com.dataweaver.api.pattern.validators.types.RequiredField;
import com.dataweaver.api.repository.DatabaseConnectionRepository;
import com.dataweaver.api.service.DatasourceConnectionService;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnectionValidator extends AbstractValidator<DatabaseConnection> {

    private final DatabaseConnectionRepository databaseConnectionRepository;

    private final DatasourceConnectionService datasourceConnectionService;

    public DatabaseConnectionValidator(DatabaseConnectionRepository databaseConnectionRepository,
                                       DatasourceConnectionService datasourceConnectionService) {
        this.databaseConnectionRepository = databaseConnectionRepository;
        this.datasourceConnectionService = datasourceConnectionService;

        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(DatabaseConnection.class.getDeclaredField("host"), "host"));
            requiredFields.add(new RequiredField(DatabaseConnection.class.getDeclaredField("port"), "porta"));
            requiredFields.add(new RequiredField(DatabaseConnection.class.getDeclaredField("database"), "base"));
            requiredFields.add(new RequiredField(DatabaseConnection.class.getDeclaredField("username"), "usuario"));
            requiredFields.add(new RequiredField(DatabaseConnection.class.getDeclaredField("password"), "senha"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    @Override
    public void validate(DatabaseConnection connection) {
        super.validate(connection);

        try {
            datasourceConnectionService.test(connection);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public void validateInsert(DatabaseConnection connection) {
        validate(connection);

        if (!databaseConnectionRepository.findAll().isEmpty()) {
            throw new ApplicationGenericsException("Não é possível cadastrar mais de uma conexão de banco de dados");
        }
    }

}