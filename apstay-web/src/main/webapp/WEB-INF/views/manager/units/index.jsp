<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>

<manager:layout>
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Units</h1>
        <a href="${pageContext.request.contextPath}/manager/units/create" class="d-sm-inline-block btn btn-primary shadow-sm">
            <i class="fa-solid fa-plus fa-fw"></i> New Unit
        </a>
    </div>
    
    <shared:notice-template />

    <table class="table table-stripped table-show-dt">
        <thead>
            <tr>
                <th>Unit Name</th>
                <th>Floor</th>
                <th>Occupancy</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="unit" items="${units}">
                <tr>
                    <td>${unit.unitName}</td>
                    <td>${unit.floorNumber}</td>
                    <td>
                        <c:choose>
                            <c:when test="${unit.occupied}">
                                Occupied
                            </c:when>
                            <c:otherwise>
                                Vacant
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/manager/units/edit/${unit.id}" class="btn btn-warning btn-sm">
                            <i class="fas fa-pen fa-sm fa-fw"></i> Edit
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty units}">
                <tr>
                    <td colspan="4" class="text-center">No units found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</manager:layout>
