package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.context.TenantContext;
import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.ValidatorException;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.utils.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

@RequiredArgsConstructor
@Service
public class TenantService {

    private final DataSource dataSource;

    private final FlywayService flywayService;

    private final UserService userService;

    private void createSchema(String schema) {
        try {
            try (Connection connection = dataSource.getConnection()) {
                ResultSet resultSet = connection.createStatement().executeQuery(
                        "select schema_name as schema " +
                                "  from information_schema.schemata " +
                                " where schema_name = '" + schema + "'");

                if (resultSet.next()) {
                    throw new ApplicationGenericsException("Tenant informado j́á existe");
                }

                connection.prepareStatement("create schema " + schema).executeUpdate();

                flywayService.migrateAndRepair(schema);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public User createTenantAndUser(User user) {
        if (StringUtil.isNullOrEmpty(user.getSchema())) throw new ValidatorException("Deve ser informado o tenant");

        createSchema(user.getSchema());

        TenantContext.setCurrentTenant(user.getSchema());

        return userService.insert(user);
    }

}