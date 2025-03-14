<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<manager:layout title="Gender Distribution Report">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Gender Distribution Report</h1>
    </div>

    <div class="row">
        <div class="col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Gender Distribution</h6>
                </div>
                <div class="card-body">
                    <div class="chart-pie pt-4 pb-2">
                        <canvas id="genderPieChart"></canvas>
                    </div>
                    <div class="mt-4 text-center small">
                        <span class="mr-2">
                            <i class="fas fa-circle text-primary"></i> Male
                        </span>
                        <span class="mr-2">
                            <i class="fas fa-circle text-success"></i> Female
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-6">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Gender Statistics</h6>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>Gender</th>
                                    <th>Count</th>
                                    <th>Percentage</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Male</td>
                                    <td>${genderData.genderCounts().get("MALE")}</td>
                                    <td><fmt:formatNumber value="${genderData.genderPercentages().get('MALE')}" pattern="0.0"/>%</td>
                                </tr>
                                <tr>
                                    <td>Female</td>
                                    <td>${genderData.genderCounts().get("FEMALE")}</td>
                                    <td><fmt:formatNumber value="${genderData.genderPercentages().get('FEMALE')}" pattern="0.0"/>%</td>
                                </tr>
                                <tr>
                                    <td><strong>Total</strong></td>
                                    <td><strong>${genderData.totalCount()}</strong></td>
                                    <td><strong>
                                        <c:set var="totalPercentage" value="${genderData.genderPercentages().get('MALE') + genderData.genderPercentages().get('FEMALE')}" />
                                        <fmt:formatNumber value="${totalPercentage}" pattern="0.0"/>%
                                    </strong></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
            Chart.defaults.global.defaultFontColor = '#858796';

            var ctx = document.getElementById("genderPieChart");
            var genderPieChart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: ["Male", "Female"],
                    datasets: [{
                        data: [
                            ${genderData.genderCounts().get("MALE")}, 
                            ${genderData.genderCounts().get("FEMALE")}
                        ],
                        backgroundColor: ['#4e73df', '#1cc88a'],
                        hoverBackgroundColor: ['#2e59d9', '#17a673'],
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
                }
            });
        });
    </script>
</manager:layout>