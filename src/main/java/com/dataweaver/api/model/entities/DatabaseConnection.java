package com.dataweaver.api.model.entities;

import com.dataweaver.api.pattern.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "database_connection")
@EqualsAndHashCode(of = "id", callSuper = false)
public class DatabaseConnection extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_database_connection", sequenceName = "gen_id_database_connection", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_database_connection")
    private Integer id;

    @Column(name = "host", nullable = false)
    private String host;

    @Column(name = "port", nullable = false)
    private Integer port;

    @Column(name = "database", nullable = false)
    private String database;

    @Column(name = "username", nullable = false)
    private String username;

    @Transient
    private String password;

    public String getPortugueseClassName() {
        return "conexão de banco de dados";
    }

}