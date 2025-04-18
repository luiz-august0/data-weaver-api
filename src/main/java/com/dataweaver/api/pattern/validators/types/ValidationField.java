package com.dataweaver.api.pattern.validators.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class ValidationField {

    private Field field;

    private String portugueseField;

}