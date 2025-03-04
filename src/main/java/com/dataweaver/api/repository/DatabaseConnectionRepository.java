package com.dataweaver.api.repository;

import com.dataweaver.api.model.entities.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DatabaseConnectionRepository extends
        JpaRepository<DatabaseConnection, Integer>,
        PagingAndSortingRepository<DatabaseConnection, Integer>,
        JpaSpecificationExecutor<DatabaseConnection> {
}
