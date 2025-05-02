package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.context.UserContext;
import com.dataweaver.api.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityManagerService {

    private final DatasourceConnectionService datasourceConnectionService;

    private final DatabaseConnectionService databaseConnectionService;

    private final TokenService tokenService;

    @Autowired
    private HttpServletRequest request;

    public void updateEntityManager() {
        UserContext.clearEntityManager();

        UserContext.setEntityManager(
                datasourceConnectionService.getEntityManager(
                        datasourceConnectionService.getUserEntityManagerFactory(
                                databaseConnectionService.getConnection(),
                                getDatabasePassword(request)
                        )
                )
        );
    }

    private String getDatabasePassword(HttpServletRequest request) {
        return tokenService.getDatabasePasswordClaimFromToken(TokenUtil.getTokenFromRequest(request));
    }

}