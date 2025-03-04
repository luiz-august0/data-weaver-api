package com.dataweaver.api.service;

import com.dataweaver.api.config.multitenancy.TenantContext;
import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumResourceNotFoundException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.dataweaver.api.model.beans.TokenBean;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.model.records.AuthenticationRecord;
import com.dataweaver.api.repository.UserRepository;
import com.dataweaver.api.utils.StringUtil;
import com.dataweaver.api.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    @Autowired
    private HttpServletRequest request;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final UserService userService;

    public TokenBean login(AuthenticationRecord authenticationRecord) {
        var loginPassword = new UsernamePasswordAuthenticationToken(authenticationRecord.login(), authenticationRecord.password());
        EnumUnauthorizedException userInactiveEnum = EnumUnauthorizedException.USER_INACTIVE;

        try {
            Authentication session = authenticationManager.authenticate(loginPassword);
            User user = (User) session.getPrincipal();

            if (!user.getActive()) throw new ApplicationGenericsException(userInactiveEnum);

            String accessToken = tokenService.generateToken(user, authenticationRecord.databasePassword());
            String refreshToken = tokenService.generateRefreshToken(user);

            return makeTokenBeanFromUser(user, accessToken, refreshToken);
        } catch (RuntimeException e) {
            if (e.getClass() == BadCredentialsException.class) {
                throw new ApplicationGenericsException(EnumUnauthorizedException.WRONG_CREDENTIALS);
            } else {
                if (e.getMessage().equals(userInactiveEnum.getMessage())) {
                    throw new ApplicationGenericsException(userInactiveEnum);
                } else {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            }
        }
    }

    public TokenBean refreshToken(TokenBean tokenBeanRequest) {
        User user;
        String[] subject = new String(
                Base64.getDecoder().decode(tokenService.validateToken(tokenBeanRequest.getRefreshToken()).getBytes())
        ).split("-");
        Integer userId = Integer.parseInt(subject[0]);
        String schema = subject[1];

        TenantContext.setCurrentTenant(schema);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ApplicationGenericsException(
                    EnumResourceNotFoundException.RESOURCE_NOT_FOUND, new User().getPortugueseClassName(), userId
            );
        }

        String accessToken = tokenService.generateToken(user, null);
        String newRefreshToken = tokenService.generateRefreshToken(user);

        return makeTokenBeanFromUser(user, accessToken, newRefreshToken);
    }

    public TokenBean getSession() {
        User user = userService.findAndValidate(userService.getUserByContext().getId());

        if (!user.getActive()) throw new ApplicationGenericsException(EnumUnauthorizedException.USER_INACTIVE);

        return makeTokenBeanFromUser(user, TokenUtil.getTokenFromRequest(request), tokenService.generateRefreshToken(user));
    }

    @Transactional
    public User updateSessionUser(User user) {
        return userService.updateContextUser(user);
    }

    private TokenBean makeTokenBeanFromUser(User user, String accessToken, String refreshToken) {
        TokenBean tokenBean = new TokenBean();
        tokenBean.setUserId(user.getId());
        tokenBean.setLogin(user.getLogin());
        tokenBean.setRole(user.getRole());
        tokenBean.setPhoto(user.getPhoto());
        tokenBean.setEnterpriseName(StringUtil.capitalizeAndShorthand(user.getSchema()));

        tokenBean.setAccessToken(accessToken);
        tokenBean.setRefreshToken(refreshToken);

        return tokenBean;
    }

}

