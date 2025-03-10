package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.beans.MultipartBean;
import com.dataweaver.api.model.entities.User;
import com.dataweaver.api.model.enums.EnumUserRole;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserDTO extends AbstractDTO<User> {

    private Integer id;

    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private EnumUserRole role;

    private Boolean active;

    private String photo;

    private String schema;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartBean photoMultipart;

}