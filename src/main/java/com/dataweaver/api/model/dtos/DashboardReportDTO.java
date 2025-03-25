package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.entities.DashboardReport;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class DashboardReportDTO extends AbstractDTO<DashboardReport> {

    private Integer id;

    private ReportDTO report;

    private Integer sort;

}