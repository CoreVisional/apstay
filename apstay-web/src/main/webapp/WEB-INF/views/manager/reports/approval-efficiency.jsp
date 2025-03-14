<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<manager:layout title="Approval Efficiency Report">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Registration Approval Efficiency</h1>
    </div>

    <!-- Summary Statistics Cards -->
    <div class="row">
        <div class="col-xl-4 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Average Approval Time</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                <fmt:formatNumber value="${reportData.averageApprovalTime()}" pattern="0.0"/> days
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-clock fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-xl-4 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                Approval Rate</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                <fmt:formatNumber value="${reportData.approvalRate()}" pattern="0"/>%
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-check-circle fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-xl-4 col-md-6 mb-4">
            <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                Pending Registrations</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.pendingCount()}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-hourglass-half fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Approval Status Distribution -->
    <div class="row">
        <div class="col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Registration Status Distribution</h6>
                </div>
                <div class="card-body">
                    <div class="chart-pie pt-4 pb-2">
                        <canvas id="approvalStatusChart"></canvas>
                    </div>
                    <div class="mt-4 text-center small">
                        <span class="mr-2">
                            <i class="fas fa-circle text-success"></i> Approved
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-danger"></i> Rejected
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-warning"></i> Pending
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Recent Registrations Table -->
        <div class="col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Recent Registrations</h6>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>Applicant</th>
                                    <th>Unit</th>
                                    <th>Days to Process</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${reportData.recentRegistrations()}" var="registration">
                                    <tr>
                                        <td>${registration.get('applicantName')}</td>
                                        <td>${registration.get('unitName')}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${registration.get('daysToProcess') != null}">
                                                    ${registration.get('daysToProcess')}
                                                </c:when>
                                                <c:otherwise>
                                                    Pending
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${registration.get('status') == 'Approved'}">
                                                    <span class="badge badge-success">Approved</span>
                                                </c:when>
                                                <c:when test="${registration.get('status') == 'Rejected'}">
                                                    <span class="badge badge-danger">Rejected</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-warning">Pending</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
            var statusData = [
                <c:forEach items="${reportData.statusDistribution()}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];

            // Pie Chart - Approval Status
            var statusCtx = document.getElementById("approvalStatusChart");
            var approvalStatusChart = new Chart(statusCtx, {
                type: 'doughnut',
                data: {
                    labels: ["Approved", "Rejected", "Pending"],
                    datasets: [{
                        data: statusData,
                        backgroundColor: ['#1cc88a', '#e74a3b', '#f6c23e'],
                        hoverBackgroundColor: ['#17a673', '#be2617', '#dda20a'],
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
        });
    </script>
</manager:layout>