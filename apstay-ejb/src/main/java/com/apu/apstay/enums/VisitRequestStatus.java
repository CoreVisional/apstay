package com.apu.apstay.enums;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexc
 */
public enum VisitRequestStatus {
    SUBMITTED,
    REACHED,
    CANCELLED,
    CLOSED;
    
    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
