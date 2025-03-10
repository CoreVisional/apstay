<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<manager:layout>
    <div class="row">
        <div class="col-12 mb-4">
            <h1 class="h3 mb-0 text-gray-800">Manager Dashboard</h1>
            <p class="mb-4">Welcome, [Manager Name]! Here's an overview of key metrics for your management duties.</p>
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
                            <div class="h5 mb-0 font-weight-bold text-gray-800">[Pending Approvals Count]</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-user-clock fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
                <a class="card-footer text-warning" href="${pageContext.request.contextPath}/manager/accounts/pending.jsp">
                    View Pending Accounts <i class="fas fa-arrow-right"></i>
                </a>
            </div>
        </div>

        <div class="col-xl-6 col-md-6 mb-4">
            <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                Units Overview</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">[Units Count]</div>
                            <div class="mt-2 text-muted small">
                                [Units Overview Summary]
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-building fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
                <a class="card-footer text-info" href="${pageContext.request.contextPath}/manager/units/index.jsp">
                    View All Units <i class="fas fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </div>
</manager:layout>
