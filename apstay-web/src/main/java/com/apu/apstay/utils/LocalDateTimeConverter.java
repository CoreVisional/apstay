package com.apu.apstay.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 *
 * @author alexc
 */
public class LocalDateTimeConverter extends AbstractConverter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public LocalDateTimeConverter() {
        super(LocalDateTime.class);
    }

    @Override
    protected Object convertToType(Class type, Object value) throws Throwable {
        if (value instanceof String string) {
            return LocalDateTime.parse(string, FORMATTER);
        }
        throw conversionException(type, value);
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalDateTime.class;
    }
}
