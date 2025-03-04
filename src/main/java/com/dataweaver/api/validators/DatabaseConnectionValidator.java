package com.dataweaver.api.validators;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.pattern.validators.AbstractValidator;
import com.dataweaver.api.repository.DatabaseConnectionRepository;

public class DatabaseConnectionValidator extends AbstractValidator<DatabaseConnection> {

    private final DatabaseConnectionRepository databaseConnectionRepository;

    public DatabaseConnectionValidator(DatabaseConnectionRepository databaseConnectionRepository) {
        this.databaseConnectionRepository = databaseConnectionRepository;
    }

    @Override
    public void validate(DatabaseConnection connection) {
        super.validate(connection);
    }

    public void validateInsert(DatabaseConnection connection) {
        validate(connection);

        if (!databaseConnectionRepository.findAll().isEmpty()) {
            throw new ApplicationGenericsException("Não é possível cadastrar mais de uma conexão de banco de dados");
        }
    }

}