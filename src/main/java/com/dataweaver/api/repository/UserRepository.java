package com.dataweaver.api.repository;

import com.dataweaver.api.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer>,
        JpaSpecificationExecutor<User> {
    Optional<User> findByLogin(String login);

    @Query(value = "" +
            " select count(id) > 0 " +
            "   from public.users " +
            "  where login = :login " +
            "    and id <> :id ", nativeQuery = true)
    Boolean existsByLoginAndIdIsNot(String login, Integer id);

    @Query(value = "" +
            " select * " +
            "   from public.users " +
            "  where login = :login " +
            "  limit 1 ", nativeQuery = true)
    Optional<User> findByGlobalLogin(String login);

}
