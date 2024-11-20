package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.entities.ReportFilter;
import com.dataweaver.api.model.enums.EnumReportFilterType;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class ReportFilterDTO extends AbstractDTO<ReportFilter> {

    private Integer id;

    private String label;

    private String parameter;

    private EnumReportFilterType type;

    private String standardValue;

    private Integer order;

}