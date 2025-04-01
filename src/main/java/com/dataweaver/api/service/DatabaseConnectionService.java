package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.repository.DatabaseConnectionRepository;
import com.dataweaver.api.validators.DatabaseConnectionValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public DatabaseConnection create(DatabaseConnection databaseConnection) {
        databaseConnectionValidator.validateInsert(databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    @Transactional
    public DatabaseConnection edit(DatabaseConnection databaseConnection) {
        Optional<DatabaseConnection> optionalConnection = databaseConnectionRepository.findAll().stream().findFirst();

        if (optionalConnection.isEmpty()) return null;

        databaseConnection.setId(optionalConnection.get().getId());

        databaseConnectionValidator.validate(databaseConnection);

        return databaseConnectionRepository.save(databaseConnection);
    }

    public DatabaseConnection getConnection() {
        return get().orElseThrow(() -> new ApplicationGenericsException("Não há conexão de banco de dados criada"));
    }

    public Optional<DatabaseConnection> get() {
        return databaseConnectionRepository
                .findAll()
                .stream()
                .findFirst();
    }

}