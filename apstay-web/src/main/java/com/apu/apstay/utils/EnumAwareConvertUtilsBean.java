package com.apu.apstay.utils;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

/**
 *
 * @author alexc
 */
public class EnumAwareConvertUtilsBean extends ConvertUtilsBean {
    private final EnumConverter enumConverter = new EnumConverter();

    @Override
    public Converter lookup(Class clazz) {
        Converter converter = super.lookup(clazz);
        if (converter == null && clazz.isEnum()) {
            return enumConverter;
        } else {
            return converter;
        }
    }

    private class EnumConverter implements Converter {
        @Override
        public Object convert(Class type, Object value) {
            if (value == null) {
                return null;
            }
            if (!(value instanceof String)) {
                throw new ConversionException("Value is not a String: " + value);
            }
            String str = ((String) value).trim();
            if (str.isEmpty()) {
                return null;
            }
            try {
                // Note: This assumes the String exactly matches one of the enum constant names.
                return Enum.valueOf(type, str);
            } catch (IllegalArgumentException e) {
                throw new ConversionException("Invalid value for enum " + type.getName() + ": " + value, e);
            }
        }
    }
}