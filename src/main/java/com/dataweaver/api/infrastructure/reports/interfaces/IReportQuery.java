package com.dataweaver.api.infrastructure.reports.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IReportQuery {

    Page<Map<String, Object>> getQueryResult(String sql, Pageable pageable, Map<IReportFilter, Object> filters, List<? extends IReportColumn> reportColumns);

    Map<String, Object> getTotalizersQueryResult(String sql, Map<IReportFilter, Object> filters);

}
