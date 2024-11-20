package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.reports.ReportFilterMapper;
import com.dataweaver.api.infrastructure.reports.interfaces.IReportQuery;
import com.dataweaver.api.model.entities.Report;
import com.dataweaver.api.repository.ReportRepository;
import com.dataweaver.api.validators.ReportValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReportService extends AbstractService<ReportRepository, Report, ReportValidator> {

    private final IReportQuery reportQuery;
    private final ReportRepository reportRepository;

    ReportService(ReportRepository reportRepository, IReportQuery reportQuery) {
        super(reportRepository, new Report(), new ReportValidator());
        this.reportQuery = reportQuery;
        this.reportRepository = reportRepository;
    }

    public Page<Map<String, Object>> getReport(Integer id, Map<String, Object> filters, Pageable pageable) {
        Report report = findAndValidate(id);

        return reportQuery.getQueryResult(
                report.getSql(),
                pageable,
                ReportFilterMapper.mapFilters(filters, report.getFilters()),
                report.getColumns());
    }

    public Map<String, Object> getReportTotalizers(Integer id, Map<String, Object> filters) {
        Report report = findAndValidate(id);

        return reportQuery.getTotalizersQueryResult(report.getSqlTotalizers(), ReportFilterMapper.mapFilters(filters, report.getFilters()));
    }

    public Report findByKey(String key) {
        return reportRepository.findFirstByKey(key);
    }

}