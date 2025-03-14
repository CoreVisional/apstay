<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<manager:layout>
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Visit Request Analysis</h1>
    </div>

    <!-- Summary Statistics Cards -->
    <div class="row">
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Total Visit Requests</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.totalVisitRequests()}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-calendar fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                Reached Requests</div>
                            <c:set var="reachedPercentage" value="${reportData.totalVisitRequests() > 0 ? reportData.reachedRequests() * 100 / reportData.totalVisitRequests() : 0}" />
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                ${reportData.reachedRequests()} (<fmt:formatNumber value="${reachedPercentage}" pattern="0"/>%)
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-check-circle fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                Submitted Requests</div>
                            <c:set var="submittedPercentage" value="${reportData.totalVisitRequests() > 0 ? reportData.submittedRequests() * 100 / reportData.totalVisitRequests() : 0}" />
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                ${reportData.submittedRequests()} (<fmt:formatNumber value="${submittedPercentage}" pattern="0"/>%)
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-clock fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-danger shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                                Cancelled Requests</div>
                            <c:set var="cancelledPercentage" value="${reportData.totalVisitRequests() > 0 ? reportData.cancelledRequests() * 100 / reportData.totalVisitRequests() : 0}" />
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                ${reportData.cancelledRequests()} (<fmt:formatNumber value="${cancelledPercentage}" pattern="0"/>%)
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-times-circle fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Visit Requests by Day of Week -->
    <div class="row">
        <div class="col-xl-8 col-lg-7">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Visit Requests by Day of Week</h6>
                </div>
                <div class="card-body">
                    <div class="chart-bar">
                        <canvas id="visitsByDayChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <!-- Visit Status Distribution -->
        <div class="col-xl-4 col-lg-5">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Visit Status Distribution</h6>
                </div>
                <div class="card-body">
                    <div class="chart-pie pt-4 pb-2">
                        <canvas id="visitStatusChart"></canvas>
                    </div>
                    <div class="mt-4 text-center small">
                        <span class="mr-2">
                            <i class="fas fa-circle text-success"></i> Reached
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-warning"></i> Submitted
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-danger"></i> Cancelled
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Most Visited Units Table -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Most Visited Units</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="visitedUnitsTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Unit</th>
                            <th>Floor</th>
                            <th>Total Visits</th>
                            <th>Reached Visits</th>
                            <th>Cancelled Visits</th>
                            <th>% of Total Visits</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${reportData.mostVisitedUnits()}" var="unit">
                            <tr>
                                <td>${unit.get("unitName")}</td>
                                <td>${unit.get("floorNumber")}</td>
                                <td>${unit.get("totalVisits")}</td>
                                <td>${unit.get("reachedVisits")}</td>
                                <td>${unit.get("cancelledVisits")}</td>
                                <td><fmt:formatNumber value="${unit.get('percentageOfTotal')}" pattern="0.0"/>%</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Initialize DataTables
            $('#visitedUnitsTable').DataTable();
            
            // Chart configuration
            Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
            Chart.defaults.global.defaultFontColor = '#858796';

            // Prepare chart data
            var statusData = [
                <c:forEach items="${reportData.statusDistributionData()}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var dayLabels = [
                <c:forEach items="${reportData.dayLabels()}" var="label" varStatus="status">
                    "${label}"<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var dayData = [
                <c:forEach items="${reportData.dayData()}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];

            // Pie Chart - Visit Status Distribution
            var statusCtx = document.getElementById("visitStatusChart");
            var visitStatusChart = new Chart(statusCtx, {
                type: 'doughnut',
                data: {
                    labels: ["Reached", "Submitted", "Cancelled"],
                    datasets: [{
                        data: statusData,
                        backgroundColor: ['#1cc88a', '#f6c23e', '#e74a3b'],
                        hoverBackgroundColor: ['#17a673', '#dda20a', '#be2617'],
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

            // Bar Chart - Visits by Day of Week
            var dayCtx = document.getElementById("visitsByDayChart");
            var visitsByDayChart = new Chart(dayCtx, {
                type: 'bar',
                data: {
                    labels: dayLabels,
                    datasets: [{
                        label: "Visit Requests",
                        backgroundColor: "#4e73df",
                        hoverBackgroundColor: "#2e59d9",
                        data: dayData,
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
                            maxBarThickness: 25,
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
                    filename:     'visit-analysis-report.pdf',
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