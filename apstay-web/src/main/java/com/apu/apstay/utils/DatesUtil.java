package com.apu.apstay.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author alexc
 */
public final class DatesUtil {
    
    private DatesUtil() {    
    }
    
     public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
         return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
     }
}
