package com.dataweaver.api.infrastructure.reports;

import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilter;
import com.dataweaver.api.model.entities.ReportFilter;
import com.dataweaver.api.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFilterMapper {

    public static Map<IReportFilter, Object> mapFilters(Map<String, Object> filters, List<ReportFilter> reportFilters) {
        Map<IReportFilter, Object> iReportFilterObjectMap = new HashMap<>();

        reportFilters.forEach(reportFilter -> {
            Object filterValue = filters.get(reportFilter.getParameter());
            Object parsedFilterValue = reportFilter.getType().getQueryParamValue(Utils.nvl(filterValue, reportFilter.getStandardValue()));

            if (Utils.isNotEmpty(parsedFilterValue)) {
                iReportFilterObjectMap.putIfAbsent(reportFilter, parsedFilterValue);
            }
        });

        return iReportFilterObjectMap;
    }

}
