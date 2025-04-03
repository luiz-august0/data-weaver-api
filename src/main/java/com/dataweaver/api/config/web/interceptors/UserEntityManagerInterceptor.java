package com.dataweaver.api.config.web.interceptors;

import com.dataweaver.api.infrastructure.context.UserContext;
import com.dataweaver.api.service.DatabaseConnectionService;
import com.dataweaver.api.service.DatasourceConnectionService;
import com.dataweaver.api.service.TokenService;
import com.dataweaver.api.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class UserEntityManagerInterceptor implements HandlerInterceptor {

    private final DatasourceConnectionService datasourceConnectionService;

    private final DatabaseConnectionService databaseConnectionService;

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserContext.clearEntityManager();

        UserContext.setEntityManager(
                datasourceConnectionService.getEntityManager(
                        datasourceConnectionService.getUserEntityManagerFactory(
                                databaseConnectionService.getConnection(),
                                getDatabasePassword(request)
                        )
                )
        );

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContext.clearEntityManager();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clearEntityManager();
    }

    private String getDatabasePassword(HttpServletRequest request) {
        return tokenService.getDatabasePasswordClaimFromToken(TokenUtil.getTokenFromRequest(request));
    }

}