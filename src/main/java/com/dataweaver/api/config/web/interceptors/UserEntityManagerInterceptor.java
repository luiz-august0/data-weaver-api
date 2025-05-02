package com.dataweaver.api.config.web.interceptors;

import com.dataweaver.api.infrastructure.context.UserContext;
import com.dataweaver.api.service.UserEntityManagerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class UserEntityManagerInterceptor implements HandlerInterceptor {

    private final UserEntityManagerService userEntityManagerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        userEntityManagerService.updateEntityManager();

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

}