package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.entities.Report;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class ReportDTO extends AbstractDTO<Report> {

    private Integer id;

    private String name;

    private String title;

    private String key;

    private String sql;

    private String sqlTotalizers;

    private Boolean active;

    private List<ReportColumnDTO> columns;

    private List<ReportFilterDTO> filters;

    @Transient
    private boolean hasTotalizers;

}