package com.dataweaver.api.model.enums;

import com.dataweaver.api.infrastructure.exporters.pdf.enums.EnumAlignment;
import com.dataweaver.api.pattern.enums.IEnum;
import lombok.Getter;

@Getter
public enum EnumColumnAlign implements IEnum {

    LEFT("left", "Left", EnumAlignment.LEFT),
    CENTER("center", "Center", EnumAlignment.CENTER),
    RIGHT("right", "Right", EnumAlignment.RIGHT);

    private String key;

    private String value;

    private EnumAlignment enumAlignment;

    EnumColumnAlign(String key, String value, EnumAlignment enumAlignment) {
        this.key = key;
        this.value = value;
        this.enumAlignment = enumAlignment;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends IEnum.Converter<EnumColumnAlign, String> {

        @Override
        public EnumColumnAlign[] getValues() {
            return EnumColumnAlign.values();
        }

    }

    @Override
    public Class<? extends IEnum.Converter<EnumColumnAlign, String>> getConverter() {
        return Converter.class;
    }

}
