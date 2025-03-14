package com.apu.apstay.staff.manager.models.viewmodels.reports;

import java.util.List;
import java.util.Map;

/**
 *
 * @author alexc
 */
public record ApprovalEfficiencyReportViewModel(
        double averageApprovalTime,
        double approvalRate,
        int pendingCount,
        List<Integer> statusDistribution,
        List<Map<String, Object>> recentRegistrations
) { }
