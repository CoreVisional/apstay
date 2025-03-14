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
                        <a href="${pageContext.request.contextPath}/manager/staffs/details/${staff.id}" class="btn btn-primary btn-sm">
                            <i class="fas fa-info fa-sm fa-fw"></i> Details
                        </a>
                        
                        <c:if test="${staff.active}">
                            <a href="${pageContext.request.contextPath}/manager/staffs/edit/${staff.id}" class="btn btn-warning btn-sm">
                                <i class="fas fa-pen fa-sm fa-fw"></i> Edit
                            </a>
                            
                            <c:if test="${staff.id != currentUserId}">
                                <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#confirmDeactivate${staff.id}">
                                    <i class="fa-solid fa-user-xmark fa-sm fa-fw"></i> Deactivate
                                </button>
                                
                                <div class="modal fade" id="confirmDeactivate${staff.id}" role="dialog">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="confirmDeactivateTitle${staff.id}">Confirm Deactivate</h5>
                                                <button type="button" class="close" data-dismiss="modal">
                                                    <span>&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p>Are you sure you want to deactivate this staff?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <form action="${pageContext.request.contextPath}/manager/staffs/deactivate" method="POST">
                                                    <input type="hidden" name="id" value="${staff.id}" />
                                                    <button type="submit" class="btn btn-danger">Deactivate</button>
                                                </form>
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                        
                        <c:if test="${!staff.active}">
                            <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#confirmReactivate${staff.id}">
                                <i class="fa-solid fa-user-check fa-sm fa-fw"></i> Reactivate
                            </button>
                            
                            <div class="modal fade" id="confirmReactivate${staff.id}" role="dialog">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="confirmReactivateTitle${staff.id}">Reactivate Staff</h5>
                                            <button type="button" class="close" data-dismiss="modal">
                                                <span>&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-footer">
                                            <form action="${pageContext.request.contextPath}/manager/staffs/reactivate" method="POST">
                                                <input type="hidden" name="id" value="${staff.id}" />
                                                <button type="submit" class="btn btn-success">Reactivate</button>
                                            </form>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</manager:layout>