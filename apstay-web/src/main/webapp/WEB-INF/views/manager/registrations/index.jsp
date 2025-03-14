<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="f" uri="/tlds/functions" %>

<manager:layout title="Residents">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h3 class="h3 mb-0 text-gray-800">Account Registrations</h3>
    </div>
    
    <shared:notice-template />

    <table class="table table-stripped table-show-dt">
        <thead>
            <tr>
                <th>Full Name</th>
                <th>Submitted On</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="registration" items="${registrations}">
                <tr>
                    <td>${registration.name()}</td>
                    <td>${f:formatLocalDateTime(registration.createdAt(), 'MM/dd/yyyy hh:mm a')}</td>
                    <td>
                        <c:choose>
                            <c:when test="${registration.status() == 'PENDING'}">
                                <span class="badge badge-warning">Pending</span>
                            </c:when>
                            <c:when test="${registration.status() == 'APPROVED'}">
                                <span class="badge badge-success">Approved</span>
                            </c:when>
                            <c:when test="${registration.status() == 'REJECTED'}">
                                <span class="badge badge-danger">Rejected</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-secondary">${registration.status()}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/manager/registrations/details/${registration.id()}" class="btn btn-primary btn-sm">
                            <i class="fas fa-info fa-sm fa-fw"></i> Details
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</manager:layout>
