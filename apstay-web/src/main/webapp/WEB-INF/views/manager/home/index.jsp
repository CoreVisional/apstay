<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<manager:layout title="Home">
    <div class="row">
        <div class="col-12 mb-4">
            <h1 class="h3 mb-0 text-gray-800">Manager Dashboard</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-xl-6 col-md-6 mb-4">
            <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                Pending Account Approvals</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${dashboard.pendingApprovals()}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-user-clock fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
                <a class="card-footer text-warning clearfix small z-1" href="${pageContext.request.contextPath}/manager/registrations">
                    <span class="float-left">View Pending Approvals</span>
                    <span class="float-right">
                        <i class="fas fa-arrow-right"></i>
                    </span>
                </a>
            </div>
        </div>
    </div>
</manager:layout>