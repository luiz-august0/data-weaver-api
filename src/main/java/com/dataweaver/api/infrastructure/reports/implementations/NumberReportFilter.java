package com.dataweaver.api.infrastructure.reports.implementations;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilterTypeOperation;
import com.dataweaver.api.utils.NumericUtil;

public class NumberReportFilter implements IReportFilterTypeOperation {

    @Override
    public Object parseQueryParamValue(Object value) {
        return NumericUtil.parseBigDecimal(value.toString());
    }

    @Override
    public void validateValue(Object value) {
        if (!NumericUtil.isNumeric(value.toString())) {
            throw new ApplicationGenericsException(EnumGenericsException.VALIDATION_ERROR_NUMBER_REPORT_FILTER);
        }
    }

}
