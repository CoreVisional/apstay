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
                        
                        <c:if test="${resident.active()}">
                            <a href="${pageContext.request.contextPath}/manager/residents/edit/${resident.id()}" class="btn btn-warning btn-sm">
                                <i class="fas fa-pen fa-sm fa-fw"></i> Edit
                            </a>
                            
                            <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#confirmDeactivate${resident.id()}">
                                <i class="fa-solid fa-user-xmark fa-sm fa-fw"></i> Deactivate
                            </button>
                            
                            <div class="modal fade" id="confirmDeactivate${resident.id()}" role="dialog">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="confirmDeactivateTitle${resident.id()}">Confirm Deactivate</h5>
                                            <button type="button" class="close" data-dismiss="modal">
                                                <span>&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Are you sure you want to deactivate this resident?</p>
                                            <p class="text-warning">
                                                <i class="fas fa-exclamation-triangle"></i> 
                                                This will make their unit available for assignment to another resident.
                                            </p>
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
                        </c:if>
                        
                        <c:if test="${!resident.active()}">
                            <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#confirmReactivate${resident.id()}">
                                <i class="fa-solid fa-user-check fa-sm fa-fw"></i> Reactivate
                            </button>
                            
                            <div class="modal fade" id="confirmReactivate${resident.id()}" role="dialog">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="confirmReactivateTitle${resident.id()}">Reactivate Resident</h5>
                                            <button type="button" class="close" data-dismiss="modal">
                                                <span>&times;</span>
                                            </button>
                                        </div>
                                        <form action="${pageContext.request.contextPath}/manager/residents/reactivate" method="post">
                                            <div class="modal-body">
                                                <p>You are about to reactivate ${resident.fullName()}.</p>
                                                
                                                <div class="form-group">
                                                    <label for="unitId${resident.id()}">Select Unit</label>
                                                    <select name="unitId" id="unitId${resident.id()}" class="form-control" required>
                                                        <option value="">-- Select Available Unit --</option>
                                                        <c:forEach var="unit" items="${resident.availableUnits()}">
                                                            <option value="${unit.id()}">${unit.unitName()}-${unit.floorNumber()}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <small class="form-text text-muted">A resident must be assigned to an available unit upon reactivation.</small>
                                                </div>
                                                
                                                <c:if test="${empty resident.availableUnits()}">
                                                    <div class="alert alert-warning">
                                                        <i class="fas fa-exclamation-circle"></i> No available units found. You must have at least one available unit to reactivate a resident.
                                                    </div>
                                                </c:if>
                                                
                                                <input type="hidden" name="id" value="${resident.id()}" />
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                <button type="submit" class="btn btn-success" ${empty resident.availableUnits() ? 'disabled' : ''}>
                                                    Reactivate
                                                </button>
                                            </div>
                                        </form>
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