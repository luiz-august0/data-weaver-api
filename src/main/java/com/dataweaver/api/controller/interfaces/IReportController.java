package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.model.dtos.ReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static com.dataweaver.api.constants.Paths.prefixPath;

@RequestMapping(IReportController.PATH)
public interface IReportController extends IAbstractAllGetController<ReportDTO> {

    String PATH = prefixPath + "/report";

    @GetMapping("/{id}/result")
    Page<Map<String, Object>> getReport(@PathVariable("id") Integer id,
                                        @RequestParam(required = false) Map<String, Object> filters,
                                        Pageable pageable);

    @GetMapping("/{id}/totalizers")
    Map<String, Object> getReportTotalizers(@PathVariable("id") Integer id, @RequestParam(required = false) Map<String, Object> filters);

    @GetMapping("/key/{key}")
    ReportDTO findByKey(@PathVariable("key") String key);

}