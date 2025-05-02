package com.dataweaver.api.service;

import com.dataweaver.api.infrastructure.exporters.pdf.PdfExporter;
import com.dataweaver.api.infrastructure.exporters.pdf.bean.PdfColumnValue;
import com.dataweaver.api.infrastructure.exporters.pdf.bean.PdfTotalizer;
import com.dataweaver.api.infrastructure.reports.ReportFilterMapper;
import com.dataweaver.api.infrastructure.reports.ReportQueryExecutor;
import com.dataweaver.api.model.entities.Report;
import com.dataweaver.api.model.entities.ReportColumn;
import com.dataweaver.api.model.entities.ReportFilter;
import com.dataweaver.api.repository.ReportRepository;
import com.dataweaver.api.utils.DateUtil;
import com.dataweaver.api.utils.Utils;
import com.dataweaver.api.validators.ReportValidator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService extends AbstractService<ReportRepository, Report, ReportValidator> {

    private final ReportQueryExecutor reportQueryExecutor;
    private final ReportRepository reportRepository;
    private final UserEntityManagerService userEntityManagerService;

    ReportService(ReportQueryExecutor reportQueryExecutor,
                  ReportRepository reportRepository,
                  UserEntityManagerService userEntityManagerService) {
        super(reportRepository, new Report(), new ReportValidator());
        this.reportQueryExecutor = reportQueryExecutor;
        this.reportRepository = reportRepository;
        this.userEntityManagerService = userEntityManagerService;
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

    public ResponseEntity<ByteArrayResource> getReportPdf(Integer id, Map<String, Object> filters, Pageable pageable) {
        Report report = findAndValidate(id);

        Map<String, Object> totalizersMap = getReportTotalizers(id, filters);

        userEntityManagerService.updateEntityManager();

        List<Map<String, Object>> resultList = reportQueryExecutor.getQueryResultList(
                report.getSql(),
                pageable,
                ReportFilterMapper.mapFilters(filters, report.getFilters()),
                report.getColumns());

        List<PdfColumnValue> result = new ArrayList<>();

        resultList.forEach((map) -> {
            result.addAll(
                    report.getColumns()
                            .stream()
                            .map(reportColumn -> new PdfColumnValue(
                                    map.get(reportColumn.getField()).toString(),
                                    reportColumn.getAlign().getEnumAlignment().getElement()))
                            .toList()
            );
        });

        List<PdfTotalizer> totalizers = totalizersMap.keySet()
                .stream()
                .map(totalizer -> new PdfTotalizer(totalizer, totalizersMap.get(totalizer).toString()))
                .toList();

        List<PdfColumnValue> columns = report.getColumns()
                .stream()
                .map(reportColumn -> new PdfColumnValue(
                        reportColumn.getHeaderName(),
                        reportColumn.getHeaderAlign().getEnumAlignment().getElement()))
                .toList();

        return PdfExporter.returnResponse(
                new PdfExporter(
                        report.getTitle(),
                        columns,
                        result,
                        totalizers
                ),
                report.getTitle() + " - " + DateUtil.formatDDMMYYYYHHMM(DateUtil.getDate())
        );
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