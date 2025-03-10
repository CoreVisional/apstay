package com.apu.apstay.enums;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexc
 */
public enum Gender {
    MALE,
    FEMALE;
    
    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
