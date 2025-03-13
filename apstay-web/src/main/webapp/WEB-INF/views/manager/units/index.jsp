<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>

<manager:layout title="Hostel Units">
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
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="unit" items="${units}">
                <tr>
                    <td>${unit.unitName()}</td>
                    <td>${unit.floorNumber()}</td>
                    <td>
                        ${unit.currentOccupancy()} / ${unit.capacity()}
                        <c:if test="${unit.currentOccupancy() == 0}">
                            <span class="badge badge-light text-dark">Empty</span>
                        </c:if>
                        <c:if test="${unit.currentOccupancy() >= unit.capacity()}">
                            <span class="badge badge-danger">Full</span>
                        </c:if>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${unit.active()}">
                                <span class="badge badge-success">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-secondary">Inactive</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/manager/units/edit/${unit.id()}" class="btn btn-warning btn-sm">
                            <i class="fas fa-pen fa-sm fa-fw"></i> Edit
                        </a>
                        
                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteUnitModal${unit.id()}">
                            <i class="fas fa-trash fa-sm fa-fw"></i> Delete
                        </button>

                        <div class="modal fade" id="deleteUnitModal${unit.id()}" tabindex="-1" role="dialog" aria-labelledby="deleteUnitModalLabel${unit.id()}" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="deleteUnitModalLabel${unit.id()}">Confirm Deletion</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Are you sure you want to delete unit <strong>${unit.unitName()}</strong>?</p>
                                        
                                        <c:if test="${unit.currentOccupancy() > 0}">
                                            <div class="alert alert-warning">
                                                <i class="fas fa-exclamation-triangle fa-fw"></i> 
                                                <span>This unit is currently occupied by ${unit.currentOccupancy()} resident(s).</span>
                                                <p class="mt-2 mb-0">You need to reassign all resident(s) to a different unit before this unit can be deleted. Please go to the Residents section for unit reassignment.</p>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                        
                                        <c:if test="${unit.currentOccupancy() == 0}">
                                            <form action="${pageContext.request.contextPath}/manager/units/delete" method="POST">
                                                <input type="hidden" name="id" value="${unit.id()}" />
                                                <button type="submit" class="btn btn-danger">Delete Unit</button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</manager:layout>