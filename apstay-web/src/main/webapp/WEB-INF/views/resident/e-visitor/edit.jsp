<%@ taglib prefix="resident" tagdir="/WEB-INF/tags/resident" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.apu.apstay.enums.VisitRequestStatus" %>
<%@ taglib prefix="f" uri="/tlds/functions" %>

<resident:layout>
    <div class="container-fluid">
    <h3>Edit Visit Request</h3>
    <hr />
    <shared:notice-template />

    <div class="row">
        <div class="col-md-4">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Visitor Information</h6>
                </div>
                <div class="card-body">
                    <p><strong>Visitor Name:</strong> ${visit.name()}</p>
                    <p><strong>Verification Code:</strong> ${visit.verificationCode()}</p>
                    <p>
                        <strong>Arrival Date/Time:</strong>
                        ${f:formatLocalDateTime(visit.arrivalDateTime(), 'MM/dd/yyyy hh:mm a')}
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Update Status</h6>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/resident/visits/edit/${visit.id()}" method="POST">
                        <div class="form-group">
                            <label for="status" class="control-label">Status</label>
                            <select class="form-control" id="status" name="status" required>
                                <option value="">Select Status</option>
                                <c:forEach items="<%= VisitRequestStatus.values() %>" var="statusOption">
                                    <c:if test="${statusOption.name() eq 'CANCELLED' || statusOption.name() eq 'CLOSED'}">
                                        <option value="${statusOption.name()}" ${visit.status() == statusOption ? 'selected' : ''}>
                                            <c:choose>
                                                <c:when test="${statusOption.name() eq 'CLOSED'}">
                                                    Close
                                                </c:when>
                                                <c:when test="${statusOption.name() eq 'CANCELLED'}">
                                                    Cancel
                                                </c:when>
                                                <c:otherwise>
                                                    ${statusOption.toString()}
                                                </c:otherwise>
                                            </c:choose>
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <shared:validation-error field="status" />
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary mt-3">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</resident:layout>