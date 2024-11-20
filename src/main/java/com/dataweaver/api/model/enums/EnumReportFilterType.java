package com.dataweaver.api.model.enums;

import com.dataweaver.api.infrastructure.reports.implementations.BooleanReportFilter;
import com.dataweaver.api.infrastructure.reports.implementations.DateReportFilter;
import com.dataweaver.api.infrastructure.reports.implementations.DefaultReportFilter;
import com.dataweaver.api.infrastructure.reports.implementations.NumberReportFilter;
import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilterTypeOperation;
import com.dataweaver.api.pattern.enums.IEnum;
import com.dataweaver.api.utils.Utils;
import lombok.Getter;

@Getter
public enum EnumReportFilterType implements IEnum {

    DATE("DATE", "Data", new DateReportFilter()),
    TEXT("TEXT", "Texto", new DefaultReportFilter()),
    NUMBER("NUMBER", "Número", new NumberReportFilter()),
    BOOLEAN("BOOLEAN", "Booleano", new BooleanReportFilter());

    private String key;

    private String value;

    private IReportFilterTypeOperation filterTypeOperation;

    EnumReportFilterType(String key, String value, IReportFilterTypeOperation filterTypeOperation) {
        this.key = key;
        this.value = value;
        this.filterTypeOperation = filterTypeOperation;
    }

    public Object getQueryParamValue(Object value) {
        if (Utils.isNotEmpty(value)) {
            return this.filterTypeOperation.parseQueryParamValue(value);
        }

        return null;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends IEnum.Converter<EnumReportFilterType, String> {

        @Override
        public EnumReportFilterType[] getValues() {
            return EnumReportFilterType.values();
        }

    }

    @Override
    public Class<? extends IEnum.Converter<EnumReportFilterType, String>> getConverter() {
        return Converter.class;
    }

}
