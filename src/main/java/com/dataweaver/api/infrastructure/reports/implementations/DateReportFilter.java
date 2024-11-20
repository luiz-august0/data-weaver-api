package com.dataweaver.api.infrastructure.reports.implementations;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilterTypeOperation;
import com.dataweaver.api.utils.DateUtil;
import com.dataweaver.api.utils.NumericUtil;
import com.dataweaver.api.utils.enums.EnumDateFormat;

import java.util.Calendar;
import java.util.Date;

public class DateReportFilter implements IReportFilterTypeOperation {

    @Override
    public Object parseQueryParamValue(Object value) {
        try {
            String valueStr = value.toString().trim().toUpperCase();

            if (valueStr.equals("START_MONTH")) {
                return DateUtil.getFirstDayOfMonth(new Date());
            }

            if (valueStr.equals("END_MONTH")) {
                return DateUtil.getLastDayOfMonth(new Date());
            }

            if (valueStr.startsWith("-") || valueStr.startsWith("+")) {
                return DateUtil.add(DateUtil.getDate(), Calendar.DAY_OF_MONTH, NumericUtil.parseInt(valueStr));
            }

            return DateUtil.formatStringToDate(valueStr, EnumDateFormat.YYYYMMDDTHHMMSS);
        } catch (Exception e) {
            throw new ApplicationGenericsException(EnumGenericsException.VALIDATION_ERROR_DATE_REPORT_FILTER);
        }
    }

    @Override
    public void validateValue(Object value) {
        if (!value.toString().trim().toUpperCase().matches("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}|[+-]\\d+|(START|END)_MONTH)")) {
            throw new ApplicationGenericsException(EnumGenericsException.VALIDATION_ERROR_DATE_REPORT_FILTER);
        }
    }

}
