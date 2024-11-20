package com.dataweaver.api.repository;

import com.dataweaver.api.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer>,
        JpaSpecificationExecutor<User> {
    Optional<User> findByLogin(String login);

    Boolean existsByLoginAndIdIsNot(String login, Integer id);

}
