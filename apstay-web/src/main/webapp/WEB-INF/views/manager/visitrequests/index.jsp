<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="f" uri="/tlds/functions" %>

<manager:layout title="Visit Logs">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Visit Logs</h1>
    </div>
    
    <shared:notice-template />

    <table class="table table-stripped table-show-dt">
        <thead>
            <tr>
                <th>Visitor</th>
                <th>Resident</th>
                <th>Unit</th>
                <th>Arrival Time</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${visitrequests}" var="request">
                <tr>
                    <td>${request.visitorName()}</td>
                    <td>${request.residentName()}</td>
                    <td>${request.unit()}</td>
                    <td>${f:formatLocalDateTime(request.arrivalDateTime(), 'MM/dd/yyyy hh:mm a')}</td>
                    <td>
                        <span class="badge badge-${request.statusClass()}">
                            ${request.status().toString()}
                        </span>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</manager:layout>