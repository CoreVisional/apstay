<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<manager:layout title="Unit Occupancy Report">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Unit Occupancy Report</h1>
        <a href="${pageContext.request.contextPath}/manager/reports/unit-occupancy/pdf" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
            <i class="fas fa-download fa-sm text-white-50"></i> Generate PDF
        </a>
    </div>

    <div class="row">
        <div class="col-xl-8 col-lg-7">
            <div class="row">
                <div class="col-md-6 mb-4">
                    <div class="card border-left-primary shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                        Total Units</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.totalUnits()}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-home fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6 mb-4">
                    <div class="card border-left-success shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                        Active Units</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.activeUnits()}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-toggle-on fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 mb-4">
                    <div class="card border-left-secondary shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-secondary text-uppercase mb-1">
                                        Inactive Units</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.inactiveUnits()}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-toggle-off fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 mb-4">
                    <div class="card border-left-info shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                        Fully Occupied Units</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.fullyOccupiedUnits()}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-check-circle fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6 mb-4">
                    <div class="card border-left-warning shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                        Vacant Units</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.vacantUnits()}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-door-open fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-4 col-lg-5">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-success">Overall Occupancy Rate</h6>
                </div>
                <div class="card-body">
                    <div class="chart-pie pt-4 pb-2">
                        <canvas id="occupancyPieChart"></canvas>
                    </div>
                    <div class="mt-4 text-center small">
                        <span class="mr-2">
                            <i class="fas fa-circle text-success"></i> Occupied
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-danger"></i> Vacant
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xl-6 col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-success">Occupancy Rate by Floor</h6>
                </div>
                <div class="card-body">
                    <div class="chart-bar">
                        <canvas id="floorOccupancyChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-6 col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-success">Unit Capacity Distribution</h6>
                </div>
                <div class="card-body">
                    <div class="chart-bar">
                        <canvas id="capacityDistributionChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Chart configuration
            Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
            Chart.defaults.global.defaultFontColor = '#858796';

            // Prepare chart data
            var occupancyData = [
                <c:forEach items="${reportData.occupancyChartData().get('values')}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var floorLabels = [
                <c:forEach items="${reportData.floorOccupancyData().get('labels')}" var="label" varStatus="status">
                    "${label}"<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var floorValues = [
                <c:forEach items="${reportData.floorOccupancyData().get('values')}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var capacityLabels = [
                <c:forEach items="${reportData.capacityDistributionData().get('labels')}" var="label" varStatus="status">
                    "${label}"<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var capacityValues = [
                <c:forEach items="${reportData.capacityDistributionData().get('values')}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];

            // Pie Chart - Overall Occupancy
            var occupancyCtx = document.getElementById("occupancyPieChart");
            var occupancyPieChart = new Chart(occupancyCtx, {
                type: 'doughnut',
                data: {
                    labels: ["Occupied", "Vacant"],
                    datasets: [{
                        data: occupancyData,
                        backgroundColor: ['#1cc88a', '#e74a3b'],
                        hoverBackgroundColor: ['#17a673', '#be2617'],
                        hoverBorderColor: "rgba(234, 236, 244, 1)",
                    }],
                },
                options: {
                    maintainAspectRatio: false,
                    tooltips: {
                        backgroundColor: "rgb(255,255,255)",
                        bodyFontColor: "#858796",
                        borderColor: '#dddfeb',
                        borderWidth: 1,
                        xPadding: 15,
                        yPadding: 15,
                        displayColors: false,
                        caretPadding: 10,
                    },
                    legend: {
                        display: false
                    },
                    cutoutPercentage: 80,
                },
            });

            // Bar Chart - Occupancy by Floor
            var floorCtx = document.getElementById("floorOccupancyChart");
            var floorOccupancyChart = new Chart(floorCtx, {
                type: 'bar',
                data: {
                    labels: floorLabels,
                    datasets: [{
                        label: "Occupancy Rate (%)",
                        backgroundColor: "#1cc88a",
                        hoverBackgroundColor: "#17a673",
                        data: floorValues,
                    }],
                },
                options: {
                    maintainAspectRatio: false,
                    layout: {
                        padding: {
                            left: 10,
                            right: 25,
                            top: 25,
                            bottom: 0
                        }
                    },
                    scales: {
                        xAxes: [{
                            gridLines: {
                                display: false,
                                drawBorder: false
                            },
                            ticks: {
                                maxTicksLimit: 6
                            }
                        }],
                        yAxes: [{
                            ticks: {
                                min: 0,
                                max: 100,
                                maxTicksLimit: 5,
                                padding: 10,
                                callback: function(value) {
                                    return value + '%';
                                }
                            },
                            gridLines: {
                                color: "rgb(234, 236, 244)",
                                zeroLineColor: "rgb(234, 236, 244)",
                                drawBorder: false,
                                borderDash: [2],
                                zeroLineBorderDash: [2]
                            }
                        }],
                    },
                    legend: {
                        display: false
                    },
                    tooltips: {
                        titleMarginBottom: 10,
                        titleFontColor: '#6e707e',
                        titleFontSize: 14,
                        backgroundColor: "rgb(255,255,255)",
                        bodyFontColor: "#858796",
                        borderColor: '#dddfeb',
                        borderWidth: 1,
                        xPadding: 15,
                        yPadding: 15,
                        displayColors: false,
                        caretPadding: 10,
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.yLabel + '%';
                            }
                        }
                    },
                }
            });

            // Bar Chart - Unit Capacity Distribution
            var capacityCtx = document.getElementById("capacityDistributionChart");
            var capacityDistributionChart = new Chart(capacityCtx, {
                type: 'bar',
                data: {
                    labels: capacityLabels,
                    datasets: [{
                        label: "Number of Units",
                        backgroundColor: "#36b9cc",
                        hoverBackgroundColor: "#2c9faf",
                        data: capacityValues,
                    }],
                },
                options: {
                    maintainAspectRatio: false,
                    layout: {
                        padding: {
                            left: 10,
                            right: 25,
                            top: 25,
                            bottom: 0
                        }
                    },
                    scales: {
                        xAxes: [{
                            gridLines: {
                                display: false,
                                drawBorder: false
                            },
                            ticks: {
                                maxTicksLimit: 6
                            }
                        }],
                        yAxes: [{
                            ticks: {
                                min: 0,
                                maxTicksLimit: 5,
                                padding: 10,
                            },
                            gridLines: {
                                color: "rgb(234, 236, 244)",
                                zeroLineColor: "rgb(234, 236, 244)",
                                drawBorder: false,
                                borderDash: [2],
                                zeroLineBorderDash: [2]
                            }
                        }],
                    },
                    legend: {
                        display: false
                    },
                    tooltips: {
                        titleMarginBottom: 10,
                        titleFontColor: '#6e707e',
                        titleFontSize: 14,
                        backgroundColor: "rgb(255,255,255)",
                        bodyFontColor: "#858796",
                        borderColor: '#dddfeb',
                        borderWidth: 1,
                        xPadding: 15,
                        yPadding: 15,
                        displayColors: false,
                        caretPadding: 10,
                    },
                }
            });

            // PDF Generation functionality
            document.querySelector('a.btn-primary.shadow-sm').addEventListener('click', function(e) {
                e.preventDefault();

                // Create a clone of the report content
                const element = document.querySelector('.container-fluid');

                // Options for PDF generation
                const opt = {
                    margin:       [10, 10, 10, 10],
                    filename:     'unit-occupancy-report.pdf',
                    image:        { type: 'jpeg', quality: 0.98 },
                    html2canvas:  { scale: 2 },
                    jsPDF:        { unit: 'mm', format: 'a4', orientation: 'portrait' }
                };

                // Generate PDF
                html2pdf(element, opt);
            });
        });
    </script>
</manager:layout>