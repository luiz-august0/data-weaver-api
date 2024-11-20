package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumResourceNotFoundException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.dataweaver.api.model.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            Optional<User> user = userService.findByLogin(username);

            if (user.isEmpty())
                throw new ApplicationGenericsException(EnumUnauthorizedException.WRONG_CREDENTIALS);

            return user.get();
        } catch (UsernameNotFoundException e) {
            throw new ApplicationGenericsException(EnumResourceNotFoundException.RESOURCE_NOT_FOUND);
        }
    }
}
