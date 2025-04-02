package com.dataweaver.api.model.enums;

import com.dataweaver.api.pattern.enums.IEnum;
import lombok.Getter;

@Getter
public enum EnumColumnAlign implements IEnum {

    LEFT("left", "Left"),
    CENTER("center", "Center"),
    RIGHT("right", "Right");

    private String key;

    private String value;

    EnumColumnAlign(String key, String value) {
        this.key = key;
        this.value = value;
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
