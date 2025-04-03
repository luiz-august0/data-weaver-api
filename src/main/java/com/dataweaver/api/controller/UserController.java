package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IUserController;
import com.dataweaver.api.infrastructure.context.UserContext;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.model.dtos.UserDTO;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController extends AbstractAllController<UserService, UserDTO, User> implements IUserController {

    private final UserService userService;

    UserController(UserService userService) {
        super(userService, new UserDTO(), new User());
        this.userService = userService;
    }

    @Override
    public UserDTO insert(UserDTO user) {
        return Converter.convertEntityToDTO(userService.insert(Converter.convertDTOToEntity(user, User.class)), UserDTO.class);
    }

    @Override
    public UserDTO update(Integer id, UserDTO user) {
        return Converter.convertEntityToDTO(userService.update(id, Converter.convertDTOToEntity(user, User.class)), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllFiltered(Pageable pageable, Map filters) {
        setDefaultFilters(filters);

        return super.findAllFiltered(pageable, filters);
    }

    @Override
    public Page<UserDTO> findAllFilteredAndPageable(Pageable pageable, Map filters) {
        setDefaultFilters(filters);

        return super.findAllFilteredAndPageable(pageable, filters);
    }

    private void setDefaultFilters(Map filters) {
        filters.put("id:<>:", UserContext.getUserByContext().getId());
        filters.put("login:<>:", "admin");
    }

}