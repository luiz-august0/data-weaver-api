package com.dataweaver.api.validators;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.pattern.validators.AbstractValidator;
import com.dataweaver.api.pattern.validators.types.RequiredField;
import com.dataweaver.api.repository.UserRepository;
import com.dataweaver.api.utils.StringUtil;
import com.dataweaver.api.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UserValidator extends AbstractValidator<User> {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;

        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(User.class.getDeclaredField("login"), "login"));
            requiredFields.add(new RequiredField(User.class.getDeclaredField("password"), "senha"));
            requiredFields.add(new RequiredField(User.class.getDeclaredField("role"), "nivel de acesso"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    @Override
    public void validate(User user) {
        super.validate(user);

        if (!StringUtil.isValidEmail(user.getLogin())) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.LOGIN_INVALID);
        }

        if (userRepository.existsByLoginAndIdIsNot(user.getLogin(), Utils.nvl(user.getId(), 0)))
            throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);
    }

}