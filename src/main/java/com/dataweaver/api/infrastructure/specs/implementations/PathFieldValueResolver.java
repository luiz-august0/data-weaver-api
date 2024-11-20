package com.dataweaver.api.infrastructure.specs.implementations;

import com.dataweaver.api.infrastructure.specs.interfaces.IPathFieldValueResolver;
import com.dataweaver.api.utils.DateUtil;

public class PathFieldValueResolver {

    public static IPathFieldValueResolver getPathFieldValueDateResolver() {
        return fieldValue -> DateUtil.parseStringToDate(fieldValue.toString());
    }

    public static IPathFieldValueResolver getPathFieldValueBooleanResolver() {
        return fieldValue -> Boolean.parseBoolean(fieldValue.toString());
    }

    public static IPathFieldValueResolver getPathFieldValueDefaultResolver() {
        return fieldValue -> fieldValue;
    }

}
