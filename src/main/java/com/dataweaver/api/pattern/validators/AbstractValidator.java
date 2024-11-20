package com.dataweaver.api.pattern.validators;

import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exceptions.ValidatorException;
import com.dataweaver.api.pattern.validators.types.CharacterLengthField;
import com.dataweaver.api.pattern.validators.types.NumericValueField;
import com.dataweaver.api.pattern.validators.types.RequiredField;
import com.dataweaver.api.utils.NumericUtil;
import com.dataweaver.api.utils.Utils;

import java.util.List;

public abstract class AbstractValidator<Object> {
    private List<RequiredField> requiredFields;

    private List<CharacterLengthField> characterLengthFields;

    private List<NumericValueField> numericValueFields;

    public void validate(Object object) {
        validateRequiredFields(object);
        validateCharacterLengthFields(object);
        validateNumericValueFields(object);
    }

    public void validateRequiredFields(Object object) {
        if (Utils.isNotEmpty(requiredFields)) {
            requiredFields.forEach(requiredField -> {
                requiredField.getField().setAccessible(true);

                try {
                    if (Utils.isEmpty(requiredField.getField().get(object))) {
                        throw new ValidatorException(ValidatorException.mountMessageToRequiredField(requiredField.getPortugueseField()));
                    }
                } catch (IllegalAccessException e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });
        }
    }

    public void validateCharacterLengthFields(Object object) {
        if (Utils.isNotEmpty(characterLengthFields)) {
            characterLengthFields.forEach(characterLengthField -> {
                characterLengthField.getField().setAccessible(true);

                try {
                    if (characterLengthField.getField().getType().equals(String.class)) {
                        if (characterLengthField.getMax()) {
                            if (characterLengthField.getField().get(object).toString().length() > characterLengthField.getValue()) {
                                throw new ValidatorException(ValidatorException.mountMessageToCharacterLengthField(
                                        characterLengthField.getPortugueseField(),
                                        characterLengthField.getValue(),
                                        characterLengthField.getMax()
                                ));
                            }
                        } else {
                            if (characterLengthField.getField().get(object).toString().length() < characterLengthField.getValue()) {
                                throw new ValidatorException(ValidatorException.mountMessageToCharacterLengthField(
                                        characterLengthField.getPortugueseField(),
                                        characterLengthField.getValue(),
                                        characterLengthField.getMax()
                                ));
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });
        }
    }

    public void validateNumericValueFields(Object object) {
        if (Utils.isNotEmpty(numericValueFields)) {
            numericValueFields.forEach(numericValueField -> {
                numericValueField.getField().setAccessible(true);

                try {
                    numericValueField.getNumericValidation().validate(
                            NumericUtil.parseBigDecimal(numericValueField.getField().get(object)),
                            numericValueField.getPortugueseField()
                    );
                } catch (IllegalAccessException e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });
        }
    }

    public void addListOfRequiredFields(List<RequiredField> fields) {
        requiredFields = fields;
    }

    public void addListOfCharacterLengthFields(List<CharacterLengthField> fields) {
        characterLengthFields = fields;
    }

    public void addListOfNumericValueFields(List<NumericValueField> fields) {
        numericValueFields = fields;
    }

}