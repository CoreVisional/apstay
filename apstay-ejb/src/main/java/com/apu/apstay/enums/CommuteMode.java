package com.apu.apstay.enums;

import org.apache.commons.text.WordUtils;

/**
 *
 * @author alexc
 */
public enum CommuteMode {
    PUBLIC_TRANSPORT,
    WALKING,
    DRIVING;
    
    @Override
    public String toString() {
        return WordUtils.capitalizeFully(
            this.name().toLowerCase().replace('_', ' ')
        );
    }
}
