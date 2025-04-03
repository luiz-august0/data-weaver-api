package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.context.UserContext;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.model.entities.User;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextException;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DatasourceConnectionService {

    private static final Map<User, EntityManagerFactory> USER_ENTITY_MANAGER_FACTORY_MAP = new ConcurrentHashMap<>();

    public EntityManagerFactory getUserEntityManagerFactory(DatabaseConnection connection, String databasePassword) {
        return USER_ENTITY_MANAGER_FACTORY_MAP.computeIfAbsent(UserContext.getUserByContext(), user ->
                getEntityManagerFactory(getDatasource(connection, databasePassword))
        );
    }

    public void removeUserEntityManagerFactory(User user) {
        USER_ENTITY_MANAGER_FACTORY_MAP.remove(user);
    }

    public EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    public EntityManagerFactory getEntityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.dataweaver.api.model.entities");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        factoryBean.setJpaPropertyMap(jpaProperties);

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    public DataSource getDatasource(DatabaseConnection connection, String databasePassword) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://" + connection.getHost() + ":" + connection.getPort() + "/" + connection.getDatabase());
        dataSource.setUsername(connection.getUsername());
        dataSource.setPassword(databasePassword);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

    public void test(DatabaseConnection connection) {
        DataSource dataSource = getDatasource(connection, connection.getPassword());
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory(dataSource);
        EntityManager entityManager = getEntityManager(entityManagerFactory);

        try {
            entityManager.createNativeQuery("select 1").getSingleResult();
        } catch (Exception e) {
            throw new ApplicationContextException("Ocorreu um erro ao conectar com a base: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
            ((HikariDataSource) dataSource).close();
        }
    }

}
