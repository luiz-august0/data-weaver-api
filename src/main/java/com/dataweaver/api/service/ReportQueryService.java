package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.reports.ReportQueryExecutor;
import com.dataweaver.api.utils.TokenUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class ReportQueryService extends ReportQueryExecutor {

    @Autowired
    private HttpServletRequest request;

    private final TokenService tokenService;

    private final DatabaseConnectionService databaseConnectionService;

    private final DatasourceConnectionService dataSourceConnectionService;


    @Override
    protected EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
        return dataSourceConnectionService.getEntityManager(entityManagerFactory);
    }

    @Override
    protected EntityManagerFactory getEntityManagerFactory(DataSource dataSource) {
        return dataSourceConnectionService.getEntityManagerFactory(dataSource);
    }

    @Override
    protected DataSource getDatasource() {
        return dataSourceConnectionService.getDatasource(databaseConnectionService.getConnection(), getDatabasePassword());
    }

    private String getDatabasePassword() {
        return tokenService.getDatabasePasswordClaimFromToken(TokenUtil.getTokenFromRequest(request));
    }

}