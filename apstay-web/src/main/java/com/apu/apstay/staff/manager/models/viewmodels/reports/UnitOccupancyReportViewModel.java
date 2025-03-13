package com.apu.apstay.staff.manager.models.viewmodels.reports;

import java.util.Map;

/**
 *
 * @author alexc
 */
public record UnitOccupancyReportViewModel(
        int totalUnits,
        int activeUnits,
        int inactiveUnits,
        int fullyOccupiedUnits,
        int vacantUnits,
        Map<String, Object> occupancyChartData,
        Map<String, Object> floorOccupancyData,
        Map<String, Object> capacityDistributionData
) { }