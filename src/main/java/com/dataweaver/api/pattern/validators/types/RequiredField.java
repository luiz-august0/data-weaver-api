package com.dataweaver.api.pattern.validators.types;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class RequiredField extends ValidationField {

    public RequiredField(Field field, String portugueseField) {
        super(field, portugueseField);
    }

}