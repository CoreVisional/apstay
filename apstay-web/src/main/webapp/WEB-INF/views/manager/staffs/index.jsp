<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>

<manager:layout>
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Staffs</h1>
        <a href="${pageContext.request.contextPath}/manager/staffs/create" class="d-sm-inline-block btn btn-primary shadow-sm">
            <i class="fa-solid fa-plus fa-fw"></i> New Staff
        </a>
    </div>
    
    <shared:notice-template />

    <table class="table table-stripped table-show-dt">
        <thead>
            <tr>
                <th>Full Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="staff" items="${staffs}">
                <tr>
                    <td>${staff.fullName}</td>
                    <td>${staff.email}</td>
                    <td>${staff.role}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/manager/staffs/details?id=${staff.id}" class="btn btn-primary btn-sm">
                            <i class="fas fa-info fa-sm fa-fw"></i> Details
                        </a>
                        <a href="${pageContext.request.contextPath}/manager/staffs/edit/${staff.id}" class="btn btn-warning btn-sm">
                            <i class="fas fa-pen fa-sm fa-fw"></i> Edit
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</manager:layout>