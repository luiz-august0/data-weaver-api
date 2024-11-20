package com.dataweaver.api.infrastructure.reports.implementations;

import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilterTypeOperation;

public class DefaultReportFilter implements IReportFilterTypeOperation {

    @Override
    public Object parseQueryParamValue(Object value) {
        return value;
    }

    @Override
    public void validateValue(Object value) {
    }

}
