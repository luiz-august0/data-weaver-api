package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IReportController;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.model.dtos.ReportDTO;
import com.dataweaver.api.model.entities.Report;
import com.dataweaver.api.service.ReportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ReportController extends AbstractAllController<ReportService, ReportDTO, Report> implements IReportController {

    private final ReportService reportService;

    ReportController(ReportService reportService) {
        super(reportService, new ReportDTO(), new Report());
        this.reportService = reportService;
    }

    @Override
    public Page<Map<String, Object>> getReport(Integer id, Map<String, Object> filters, Pageable pageable) {
        return reportService.getReport(id, filters, pageable);
    }

    @Override
    public Map<String, Object> getReportTotalizers(Integer id, Map<String, Object> filters) {
        return reportService.getReportTotalizers(id, filters);
    }

    @Override
    public ReportDTO findByKey(String key) {
        return Converter.convertEntityToDTO(reportService.findByKey(key), ReportDTO.class);
    }

    @Override
    public ResponseEntity<ByteArrayResource> getReportPdf(Integer id, Map<String, Object> filters, Pageable pageable) {
        return reportService.getReportPdf(id, filters, pageable);
    }

}
