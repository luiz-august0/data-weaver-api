package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.repository.DatabaseConnectionRepository;
import com.dataweaver.api.validators.DatabaseConnectionValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseConnectionService extends AbstractService<DatabaseConnectionRepository, DatabaseConnection, DatabaseConnectionValidator> {

    private final DatabaseConnectionRepository databaseConnectionRepository;

    private final DatabaseConnectionValidator databaseConnectionValidator;

    DatabaseConnectionService(DatabaseConnectionRepository databaseConnectionRepository,
                              DatasourceConnectionService datasourceConnectionService) {
        super(databaseConnectionRepository, new DatabaseConnection(), new DatabaseConnectionValidator(
                databaseConnectionRepository, datasourceConnectionService));
        this.databaseConnectionRepository = databaseConnectionRepository;
        this.databaseConnectionValidator = new DatabaseConnectionValidator(
                databaseConnectionRepository, datasourceConnectionService);
    }

    public DatabaseConnection create(DatabaseConnection databaseConnection) {
        databaseConnectionValidator.validateInsert(databaseConnection);
        prepareForSave(databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    public DatabaseConnection edit(DatabaseConnection databaseConnection) {
        Optional<DatabaseConnection> optionalConnection = databaseConnectionRepository.findAll().stream().findFirst();

        if (optionalConnection.isEmpty()) return null;

        databaseConnection.setId(optionalConnection.get().getId());

        databaseConnectionValidator.validate(databaseConnection);
        prepareForSave(databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    public DatabaseConnection getConnection() {
        return databaseConnectionRepository
                .findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ApplicationGenericsException("Não há conexão de banco de dados criada"));
    }

    private void prepareForSave(DatabaseConnection connection) {
        connection.setPassword(new BCryptPasswordEncoder().encode(connection.getPassword()));
    }

}