package com.apu.apstay.staff.manager.models.viewmodels.reports;

import java.util.Map;

/**
 *
 * @author alexc
 */
public record GenderDistributionReportViewModel(
        Map<String, Integer> genderCounts,
        Map<String, Double> genderPercentages,
        int totalCount
) { }
