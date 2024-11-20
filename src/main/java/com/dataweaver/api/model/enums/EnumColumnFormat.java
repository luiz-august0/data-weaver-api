package com.dataweaver.api.model.enums;

import com.dataweaver.api.pattern.enums.IEnum;
import com.dataweaver.api.utils.DateUtil;
import com.dataweaver.api.utils.NumericUtil;
import com.dataweaver.api.utils.StringUtil;
import lombok.Getter;

import java.util.Date;

@Getter
public enum EnumColumnFormat implements IEnum {

    DEFAULT("DEFAULT", "Padrão", value -> value),
    MONETARY("MONETARY", "Valor monetario", value -> "R$ " + StringUtil.formatMonetary(NumericUtil.parseBigDecimal(value))),
    PERCENTUAL("PERCENTUAL", "Percentual", value -> StringUtil.formatMonetary(NumericUtil.parseBigDecimal(value)) + "%"),
    DDMMYYYY("DDMMYYYY", "dd/mm/yyyy", value -> DateUtil.formatDDMMYYYY((Date) value)),
    DDMMYYYYHHMM("DDMMYYYYHHMM", "dd/mm/yyyy hh:mm", value -> DateUtil.formatDDMMYYYYHHMM((Date) value));

    private String key;

    private String value;

    private IColumnFormatter columnFormatter;

    @FunctionalInterface
    public interface IColumnFormatter {

        Object formatValue(Object value);

    }

    EnumColumnFormat(String key, String value, IColumnFormatter columnFormatter) {
        this.key = key;
        this.value = value;
        this.columnFormatter = columnFormatter;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends IEnum.Converter<EnumColumnFormat, String> {

        @Override
        public EnumColumnFormat[] getValues() {
            return EnumColumnFormat.values();
        }

    }

    @Override
    public Class<? extends IEnum.Converter<EnumColumnFormat, String>> getConverter() {
        return Converter.class;
    }

}
