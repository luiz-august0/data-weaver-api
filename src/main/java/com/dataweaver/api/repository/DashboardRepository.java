package com.dataweaver.api.repository;

import com.dataweaver.api.model.entities.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DashboardRepository extends
        JpaRepository<Dashboard, Integer>,
        PagingAndSortingRepository<Dashboard, Integer>,
        JpaSpecificationExecutor<Dashboard> {

    Dashboard findFirstByMainTrue();

}
