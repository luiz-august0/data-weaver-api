package com.dataweaver.api.infrastructure.reports.interfaces;

public interface IReportFilterTypeOperation {

    Object parseQueryParamValue(Object value);

    void validateValue(Object value);

}
