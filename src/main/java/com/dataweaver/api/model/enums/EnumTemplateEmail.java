package com.dataweaver.api.model.enums;

import com.dataweaver.api.pattern.enums.IEnum;
import lombok.Getter;

@Getter
public enum EnumTemplateEmail implements IEnum {

    RECOVERY("RECOVERY", "Recuperação", "Recuperação de senha");

    private String key;

    private String value;

    private String subject;

    EnumTemplateEmail(String key, String value, String subject) {
        this.key = key;
        this.value = value;
        this.subject = subject;
    }

    EnumTemplateEmail(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends IEnum.Converter<EnumTemplateEmail, String> {

        @Override
        public EnumTemplateEmail[] getValues() {
            return EnumTemplateEmail.values();
        }

    }

    @Override
    public Class<? extends IEnum.Converter<EnumTemplateEmail, String>> getConverter() {
        return Converter.class;
    }

}
