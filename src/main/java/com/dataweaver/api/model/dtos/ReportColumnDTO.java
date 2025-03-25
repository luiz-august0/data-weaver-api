package com.dataweaver.api.model.dtos;

import com.dataweaver.api.model.entities.ReportColumn;
import com.dataweaver.api.model.enums.EnumColumnFormat;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class ReportColumnDTO extends AbstractDTO<ReportColumn> {

    private Integer id;

    private String field;

    private String html;

    private String headerName;

    private String headerAlign;

    private String align;

    private Integer sort;

    private EnumColumnFormat format;

}