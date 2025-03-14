<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<manager:layout title="User Activity Report">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">User Activity Report</h1>
    </div>

    <div class="row">
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Total Users</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.totalUsers()}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-users fa-2x text-gray-300"></i>
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
                                Active Users</div>
                            <c:set var="activePercentage" value="${reportData.totalUsers() > 0 ? reportData.activeUsers() * 100 / reportData.totalUsers() : 0}" />
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                ${reportData.activeUsers()} (<fmt:formatNumber value="${activePercentage}" pattern="0"/>%)
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-user-check fa-2x text-gray-300"></i>
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
                                Logins Today</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.loginsToday()}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-sign-in-alt fa-2x text-gray-300"></i>
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
                                Failed Login Attempts (Last 7 Days)</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${reportData.failedLoginsLastWeek()}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-exclamation-triangle fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xl-6 col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">User Status Distribution</h6>
                </div>
                <div class="card-body">
                    <div class="chart-pie pt-4 pb-2">
                        <canvas id="userStatusChart"></canvas>
                    </div>
                    <div class="mt-4 text-center small">
                        <span class="mr-2">
                            <i class="fas fa-circle text-success"></i> Active
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-danger"></i> Inactive
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-warning"></i> Locked
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-6 col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Login Activity (Last 7 Days)</h6>
                </div>
                <div class="card-body">
                    <div class="chart-bar">
                        <canvas id="loginActivityChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Recent User Activity</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="userActivityTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Last Login</th>
                            <th>Failed Attempts</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${reportData.userActivity()}" var="user">
                            <tr>
                                <td>${user.get("username")}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.get('lastLogin') != null}">
                                            <fmt:parseDate value="${user.get('lastLogin')}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDate" type="both" />
                                            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                                        </c:when>
                                        <c:otherwise>Never</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${user.get("failedAttempts")}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.get('status') == 'Active'}">
                                            <span class="badge badge-success">Active</span>
                                        </c:when>
                                        <c:when test="${user.get('status') == 'Locked'}">
                                            <span class="badge badge-warning">Locked</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">Inactive</span>
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

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Initialize DataTables
            $('#userActivityTable').DataTable({
                order: [[1, 'desc']], // Sort by last login
                pageLength: 6
            });
            
            // Chart configuration
            Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
            Chart.defaults.global.defaultFontColor = '#858796';

            // Prepare chart data
            var statusData = [
                <c:forEach items="${reportData.statusDistribution()}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var dayLabels = [
                <c:forEach items="${reportData.dayLabels()}" var="label" varStatus="status">
                    "${label}"<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var successfulLogins = [
                <c:forEach items="${reportData.successfulLogins()}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            var failedLogins = [
                <c:forEach items="${reportData.failedLogins()}" var="value" varStatus="status">
                    ${value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];

            // Pie Chart - User Status
            var statusCtx = document.getElementById("userStatusChart");
            var userStatusChart = new Chart(statusCtx, {
                type: 'doughnut',
                data: {
                    labels: ["Active", "Inactive", "Locked"],
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

            // Bar Chart - Login Activity
            var activityCtx = document.getElementById("loginActivityChart");
            var loginActivityChart = new Chart(activityCtx, {
                type: 'bar',
                data: {
                    labels: dayLabels,
                    datasets: [{
                        label: "Successful Logins",
                        backgroundColor: "#4e73df",
                        hoverBackgroundColor: "#2e59d9",
                        data: successfulLogins,
                    },
                    {
                        label: "Failed Attempts",
                        backgroundColor: "#e74a3b",
                        hoverBackgroundColor: "#be2617",
                        data: failedLogins,
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
                        display: true,
                        position: 'top'
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
        });
    </script>
</manager:layout>