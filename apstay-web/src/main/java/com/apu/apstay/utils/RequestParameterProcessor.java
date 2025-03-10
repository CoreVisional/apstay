package com.apu.apstay.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexc
 */
public class RequestParameterProcessor {
    /**
     * Processes the request parameters by converting dashed keys to camelCase.
     *
     * @param request the HttpServletRequest
     * @return a new map with camelCase keys
     */
    public static Map<String, String[]> getProcessedParameters(HttpServletRequest request) {
        Map<String, String[]> originalParams = request.getParameterMap();
        Map<String, String[]> processedParams = new HashMap<>();

        for (Map.Entry<String, String[]> entry : originalParams.entrySet()) {
            var newKey = ParameterNameConverter.dashToCamelCase(entry.getKey());
            processedParams.put(newKey, entry.getValue());
        }
        return processedParams;
    }
}
