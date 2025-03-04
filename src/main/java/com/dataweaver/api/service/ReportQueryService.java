package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.reports.ReportQueryExecutor;
import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.utils.TokenUtil;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportQueryService extends ReportQueryExecutor {

    @Autowired
    private HttpServletRequest request;

    private final TokenService tokenService;

    private final DatabaseConnectionService databaseConnectionService;

    @Override
    protected EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    protected EntityManagerFactory getEntityManagerFactory(DataSource dataSource) {
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

    @Override
    protected DataSource getDatasource() {
        DatabaseConnection connection = databaseConnectionService.getConnection();

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://" + connection.getHost() + ":" + connection.getPort() + "/" + connection.getDatabase());
        dataSource.setUsername(connection.getUsername());
        dataSource.setPassword(getDatabasePassword());
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

    private String getDatabasePassword() {
        return tokenService.getDatabasePasswordClaimFromToken(TokenUtil.getTokenFromRequest(request));
    }

}