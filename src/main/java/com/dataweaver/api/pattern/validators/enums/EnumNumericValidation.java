package com.dataweaver.api.pattern.validators.enums;

import com.dataweaver.api.infrastructure.exceptions.ValidatorException;
import com.dataweaver.api.utils.NumericUtil;

public enum EnumNumericValidation {

    LESS_THAN_ZERO(NumericUtil::isLessThanZero, ValidatorException::mountMessageToGreaterThanOrEqualZeroField),
    LESS_OR_EQUALS_ZERO(NumericUtil::isLessOrEqualsZero, ValidatorException::mountMessageToGreaterThanZeroField);

    @FunctionalInterface
    private interface NumberValidation {
        boolean validateNumber(Number number);
    }

    @FunctionalInterface
    private interface MessageCreate {
        String createMessage(String portugueseField);
    }

    private NumberValidation numberValidation;

    private MessageCreate messageCreate;

    EnumNumericValidation(NumberValidation numberValidation, MessageCreate messageCreate) {
        this.numberValidation = numberValidation;
        this.messageCreate = messageCreate;
    }

    public void validate(Number number, String portugueseField) {
        if (numberValidation.validateNumber(number)) {
            throw new ValidatorException(messageCreate.createMessage(portugueseField));
        }
    }

}
