package com.dataweaver.api.pattern.validators.types;

import com.dataweaver.api.pattern.validators.enums.EnumNumericValidation;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class NumericValueField extends ValidationField {

    private EnumNumericValidation numericValidation;

    public NumericValueField(Field field, String portugueseField, EnumNumericValidation numericValidation) {
        super(field, portugueseField);
        this.numericValidation = numericValidation;
    }

}