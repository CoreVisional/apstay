<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<manager:layout title="Reports">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <div>
            <h1 class="h3 mb-0 text-gray-800">Reports</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-6">
            <div class="card border-left-primary shadow mb-4">
                <div class="card-body">
                    <h4 class="mb-0">Gender Distribution</h4>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/manager/reports/gender-distribution" 
                       class="btn btn-primary mb-2" 
                       target="_blank">Generate</a>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="card border-left-success shadow mb-4">
                <div class="card-body">
                    <h4 class="mb-0">Unit Occupancy</h4>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/manager/reports/unit-occupancy" 
                       class="btn btn-primary mb-2" 
                       target="_blank">Generate</a>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="card border-left-info shadow mb-4">
                <div class="card-body">
                    <h4 class="mb-0">Visit Request</h4>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/manager/reports/visit-analysis" 
                       class="btn btn-primary mb-2" 
                       target="_blank">Generate</a>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="card border-left-warning shadow mb-4">
                <div class="card-body">
                    <h4 class="mb-0">User Activity</h4>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/manager/reports/user-activity" 
                       class="btn btn-primary mb-2" 
                       target="_blank">Generate</a>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="card border-left-danger shadow mb-4">
                <div class="card-body">
                    <h4 class="mb-0">Registration Approval</h4>
                </div>
                <div class="card-footer">
                    <a href="${pageContext.request.contextPath}/manager/reports/approval-efficiency" 
                       class="btn btn-primary mb-2" 
                       target="_blank">Generate</a>
                </div>
            </div>
        </div>
    </div>
</manager:layout>