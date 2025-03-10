package com.apu.apstay.enums;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexc
 */
public enum ApprovalStatus {
    PENDING,
    APPROVED,
    REJECTED;
    
    @Override
    public String toString() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
