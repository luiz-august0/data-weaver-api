package com.dataweaver.api.service;

import com.dataweaver.api.config.multitenancy.TenantContext;
import com.dataweaver.api.constants.FlywayConstants;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.model.enums.EnumUserRole;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlywayService {

    @Autowired
    private DataSource dataSource;

    private final UserService userService;

    public void doMigrations() {
        List<String> schemas = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    " select schema_name as schema from information_schema.schemata " +
                            " where schema_name not in ('information_schema', 'public') and substring(schema_name, 1, 2) <> 'pg' "
            );

            while (resultSet.next()) schemas.add(resultSet.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        migrateAndRepair("public");

        for (String schema : schemas) {
            TenantContext.clear();

            migrateAndRepair(schema);

            String login = String.format(FlywayConstants.DEFAULT_USER, schema);

            Optional<User> user = userService.findByLogin(login);

            if (user.isEmpty()) {
                userService.insert(new User(login, schema + FlywayConstants.DEFAULT_PASSWORD, EnumUserRole.ADMIN, schema));
            }
        }
    }

    public void migrateAndRepair(String schema) {
        repair(schema);
        migrate(schema);
    }

    private Flyway configure(String schema) {
        return Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .locations(schema.equals("public") ? FlywayConstants.PUBLIC_CLASSPATH : FlywayConstants.TENANT_CLASSPATH)
                .load();
    }

    private void migrate(String schema) {
        configure(schema).migrate();
    }

    private void repair(String schema) {
        configure(schema).repair();
    }

}