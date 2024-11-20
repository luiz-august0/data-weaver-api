package com.dataweaver.api.model.beans;

import com.dataweaver.api.model.enums.EnumUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenBean {

    private Integer userId;

    private String login;

    private EnumUserRole role;

    private String photo;

    private String accessToken;

    private String refreshToken;

    private String primaryColor;

    private String secondaryColor;

    private String enterpriseName;

}