package com.dataweaver.api.pattern.enums;

import com.dataweaver.api.utils.Utils;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public interface IEnumConverter<ConcreteEnum extends IEnum, Object> extends AttributeConverter<ConcreteEnum, Object> {

    ConcreteEnum[] getValues();

    @SuppressWarnings("unchecked")
    default Object convertToDatabaseColumn(ConcreteEnum concreteEnum) {
        if (Utils.isNotEmpty(concreteEnum)) {
            return (Object) concreteEnum.getKey();
        }

        return null;
    }

    default ConcreteEnum convertToEntityAttribute(Object key) {
        if (Utils.isNotEmpty(key)) {
            return Stream.of(getValues())
                    .filter(iEnum -> iEnum.getKey().toString().equals(key.toString()))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }

}