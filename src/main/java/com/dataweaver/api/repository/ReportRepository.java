package com.dataweaver.api.repository;

import com.dataweaver.api.model.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReportRepository extends
        JpaRepository<Report, Integer>,
        PagingAndSortingRepository<Report, Integer>,
        JpaSpecificationExecutor<Report> {

    Report findFirstByKey(String key);

}
