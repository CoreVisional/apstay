<%@ taglib prefix="resident" tagdir="/WEB-INF/tags/resident" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.apu.apstay.enums.VisitRequestStatus" %>
<%@ taglib prefix="f" uri="/tlds/functions" %>

<resident:layout>
    <div class="container-fluid">
        <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h3 class="h3 mb-0 text-gray-800">Edit Visit Request</h3>

            <div>
                <c:choose>
                    <c:when test="${visit.status() eq 'REACHED'}">
                        <form action="${pageContext.request.contextPath}/resident/visits/edit/${visit.id()}" method="POST" class="d-inline">
                            <input type="hidden" name="status" value="CLOSED">
                            <button type="submit" class="d-sm-inline-block btn btn-success shadow-sm">
                                <i class="fas fa-check-circle mr-1"></i> Close Request
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${visit.status() eq 'SUBMITTED'}">
                        <form action="${pageContext.request.contextPath}/resident/visits/edit/${visit.id()}" method="POST" class="d-inline">
                            <input type="hidden" name="status" value="CANCELLED">
                            <button type="submit" class="d-sm-inline-block btn btn-danger shadow-sm">
                                <i class="fas fa-times-circle mr-1"></i> Cancel Request
                            </button>
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </div>
        
        <hr />
        <shared:notice-template />

        <div class="row">
            <div class="col-md-6">
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Visit Request Details</h6>
                    </div>
                    <div class="card-body">
                        <dl class="row">
                            <dt class="col-sm-4">Visitor Name</dt>
                            <dd class="col-sm-8">${visit.name()}</dd>
                            
                            <dt class="col-sm-4">Verification Code</dt>
                            <dd class="col-sm-8">${visit.verificationCode()}</dd>
                            
                            <dt class="col-sm-4">Arrival Date/Time</dt>
                            <dd class="col-sm-8">${f:formatLocalDateTime(visit.arrivalDateTime(), 'MM/dd/yyyy hh:mm a')}</dd>
                            
                            <dt class="col-sm-4">Current Status</dt>
                            <dd class="col-sm-8">${visit.status()}</dd>
                        </dl>
                        
                        <c:if test="${visit.status() ne 'REACHED' && visit.status() ne 'SUBMITTED'}">
                            <div class="alert alert-info mt-3">
                                <i class="fas fa-info-circle mr-2"></i>
                                No actions available for visits with status: ${visit.status()}
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</resident:layout>