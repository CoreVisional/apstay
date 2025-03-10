<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>

<manager:layout>
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Residents</h1>
    </div>
    
    <shared:notice-template />

    <table class="table table-stripped table-show-dt">
        <thead>
            <tr>
                <th>Full Name</th>
                <th>Unit</th>
                <th>Floor</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="resident" items="${residents}">
                <tr>
                    <td>${resident.fullName()}</td>
                    <td>${resident.unitName()}</td>
                    <td>${resident.floorNumber()}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/manager/residents/details?id=${resident.id()}" class="btn btn-primary btn-sm">
                            <i class="fas fa-info fa-sm fa-fw"></i> Details
                        </a>
                        <a href="${pageContext.request.contextPath}/manager/residents/edit?id=${resident.id()}" class="btn btn-warning btn-sm">
                            <i class="fas fa-pen fa-sm fa-fw"></i> Edit
                        </a>
                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#confirmDelete${resident.id()}">
                            <i class="fa-solid fa-user-xmark"></i> Deactivate
                        </button>
                        <div class="modal fade" id="confirmDelete${resident.id()}" role="dialog">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="confirmDeleteTitle${resident.id()}">Confirm Delete</h5>
                                        <button type="button" class="close" data-dismiss="modal">
                                            <span>&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Are you sure you want to deactivate this resident?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                        <form action="${pageContext.request.contextPath}/manager/residents/deactivate" method="post">
                                            <input type="hidden" name="id" value="${resident.id()}" />
                                            <button type="submit" class="btn btn-danger">Deactivate</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty residents}">
                <tr>
                    <td colspan="4" class="text-center">No residents found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</manager:layout>