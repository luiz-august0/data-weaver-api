package com.dataweaver.api.infrastructure.reports.implementations;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilterTypeOperation;

public class BooleanReportFilter implements IReportFilterTypeOperation {

    @Override
    public Object parseQueryParamValue(Object value) {
        validateValue(value);

        return Boolean.parseBoolean(value.toString());
    }

    @Override
    public void validateValue(Object value) {
        String valueStr = value.toString().trim().toLowerCase();

        if (!valueStr.equals("true") && !valueStr.equals("false")) {
            throw new ApplicationGenericsException(EnumGenericsException.VALIDATION_ERROR_NUMBER_BOOLEAN_FILTER);
        }
    }

}
