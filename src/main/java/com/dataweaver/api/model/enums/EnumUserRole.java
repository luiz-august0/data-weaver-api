package com.dataweaver.api.model.enums;

import com.dataweaver.api.pattern.enums.IEnum;
import lombok.Getter;

@Getter
public enum EnumUserRole implements IEnum {

    ADMIN("ADMIN", "Admin");

    private String key;

    private String value;

    EnumUserRole(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends IEnum.Converter<EnumUserRole, String> {

        @Override
        public EnumUserRole[] getValues() {
            return EnumUserRole.values();
        }

    }

    @Override
    public Class<? extends IEnum.Converter<EnumUserRole, String>> getConverter() {
        return Converter.class;
    }

}
