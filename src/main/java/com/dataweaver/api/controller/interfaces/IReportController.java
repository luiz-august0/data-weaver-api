package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.dtos.ReportDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IReportController.PATH)
public interface IReportController extends IAbstractAllController<ReportDTO> {

    String PATH = prefixPath + "/report";

    String RESULT_PATH = "/result/{id}";

    @GetMapping(RESULT_PATH)
    Page<Map<String, Object>> getReport(@PathVariable("id") Integer id,
                                        @RequestParam(required = false) Map<String, Object> filters,
                                        Pageable pageable);

    @GetMapping(RESULT_PATH + "/totalizers")
    Map<String, Object> getReportTotalizers(@PathVariable("id") Integer id, @RequestParam(required = false) Map<String, Object> filters);

    @GetMapping(RESULT_PATH + "/pdf")
    ResponseEntity<ByteArrayResource> getReportPdf(@PathVariable("id") Integer id,
                                                   @RequestParam(required = false) Map<String, Object> filters,
                                                   Pageable pageable);

    @GetMapping("/key/{key}")
    ReportDTO findByKey(@PathVariable("key") String key);

}