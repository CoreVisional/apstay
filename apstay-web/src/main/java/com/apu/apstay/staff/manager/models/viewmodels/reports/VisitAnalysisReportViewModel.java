package com.apu.apstay.staff.manager.models.viewmodels.reports;

import java.util.List;
import java.util.Map;

/**
 *
 * @author alexc
 */
public record VisitAnalysisReportViewModel(
        int totalVisitRequests,
        int approvedRequests,
        int pendingRequests,
        int rejectedRequests,
        List<Integer> statusDistributionData,
        List<String> dayLabels,
        List<Integer> dayData,
        List<Map<String, Object>> mostVisitedUnits
) { }
