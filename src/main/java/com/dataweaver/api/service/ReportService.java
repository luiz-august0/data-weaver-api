package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.reports.ReportFilterMapper;
import com.dataweaver.api.infrastructure.reports.ReportQueryExecutor;
import com.dataweaver.api.model.entities.Report;
import com.dataweaver.api.model.entities.ReportColumn;
import com.dataweaver.api.model.entities.ReportFilter;
import com.dataweaver.api.repository.ReportRepository;
import com.dataweaver.api.utils.Utils;
import com.dataweaver.api.validators.ReportValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService extends AbstractService<ReportRepository, Report, ReportValidator> {

    private final ReportQueryExecutor reportQueryExecutor;
    private final ReportRepository reportRepository;

    ReportService(ReportQueryExecutor reportQueryExecutor, ReportRepository reportRepository) {
        super(reportRepository, new Report(), new ReportValidator());
        this.reportQueryExecutor = reportQueryExecutor;
        this.reportRepository = reportRepository;
    }

    public Page<Map<String, Object>> getReport(Integer id, Map<String, Object> filters, Pageable pageable) {
        Report report = findAndValidate(id);

        return reportQueryExecutor.getQueryResult(
                report.getSql(),
                pageable,
                ReportFilterMapper.mapFilters(filters, report.getFilters()),
                report.getColumns());
    }

    public Map<String, Object> getReportTotalizers(Integer id, Map<String, Object> filters) {
        Report report = findAndValidate(id);

        return reportQueryExecutor.getTotalizersQueryResult(report.getSqlTotalizers(), ReportFilterMapper.mapFilters(filters, report.getFilters()));
    }

    public Report findByKey(String key) {
        return reportRepository.findFirstByKey(key);
    }

    @Override
    public Report insert(Report report) {
        updateLists(report);

        return super.insert(report);
    }

    @Override
    public Report update(Integer id, Report report) {
        updateLists(report);

        return super.update(id, report);
    }

    private void updateLists(Report report) {
        report.setColumns(Utils.nvl(report.getColumns(), new ArrayList<ReportColumn>())
                .stream()
                .peek(column -> {
                    column.setReport(report);
                })
                .collect(Collectors.toList())
        );

        report.setFilters(Utils.nvl(report.getFilters(), new ArrayList<ReportFilter>())
                .stream()
                .peek(filter -> {
                    filter.setReport(report);
                })
                .collect(Collectors.toList())
        );
    }

}