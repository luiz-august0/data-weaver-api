package com.dataweaver.api.model.entities;

import com.dataweaver.api.model.beans.MultipartBean;
import com.dataweaver.api.model.enums.EnumUserRole;
import com.dataweaver.api.pattern.entities.AbstractEntity;
import com.dataweaver.api.service.AbstractService;
import com.dataweaver.api.service.UserService;
import com.dataweaver.api.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@EqualsAndHashCode(of = "id", callSuper = false)
public class User extends AbstractEntity implements UserDetails {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_user", sequenceName = "gen_id_user", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_user")
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "role", nullable = false, length = 30)
    private EnumUserRole role;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "photo")
    private String photo;

    @Column(name = "schema")
    private String schema;

    @Transient
    private MultipartBean photoMultipart;

    @PrePersist
    @PreUpdate
    protected void onPersist() {
        if (Utils.isEmpty(active)) this.active = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getKey()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPortugueseClassName() {
        return "usuário";
    }

    @Override
    public Class<? extends AbstractService> getServiceClass() {
        return UserService.class;
    }

    @Override
    public String getObjectName() {
        return this.login;
    }

}