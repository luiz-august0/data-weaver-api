package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.dtos.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IUserController.PATH)
public interface IUserController extends IAbstractAllController<UserDTO> {

    String PATH = prefixPath + "/user";

}