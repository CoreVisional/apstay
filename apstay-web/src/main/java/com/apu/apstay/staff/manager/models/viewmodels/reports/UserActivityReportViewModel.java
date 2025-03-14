package com.apu.apstay.staff.manager.models.viewmodels.reports;

import java.util.List;
import java.util.Map;

/**
 *
 * @author alexc
 */
public record UserActivityReportViewModel(
        int totalUsers,
        int activeUsers,
        int lockedUsers,
        int inactiveUsers,
        int loginsToday,
        int failedLoginsLastWeek,
        List<Integer> statusDistribution,
        List<String> dayLabels,
        List<Integer> successfulLogins,
        List<Integer> failedLogins,
        List<Map<String, Object>> userActivity
) { }
