package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.entities.DatabaseConnection;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class DatabaseConnectionDTO extends AbstractDTO<DatabaseConnection> {

    private Integer id;

    private String host;

    private Integer port;

    private String database;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}