package com.dataweaver.api.infrastructure.reports.interfaces;

import com.dataweaver.api.model.enums.EnumColumnFormat;

public interface IReportColumn {

    String getField();

    EnumColumnFormat getFormat();

}
