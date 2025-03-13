<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<manager:layout>
    <div class="container-fluid">
        <h3>Edit Unit</h3>
        
        <hr />
        
        <shared:notice-template />
        
        <div class="row">
            <div class="col-md-4">
                <form action="${pageContext.request.contextPath}/manager/units/edit/${unit.id()}" method="POST">
                    <div class="form-group">
                        <label for="unit-name" class="control-label">Unit Name</label>
                        <input type="text" id="unit-name" name="unit-name" class="form-control" 
                               value="${unit.unitName()}" required />
                        <shared:validation-error field="unitName" />
                    </div>
                    <div class="form-group">
                        <label for="floor-number" class="control-label">Floor Number</label>
                        <input type="number" id="floor-number" name="floor-number" class="form-control" 
                               value="${unit.floorNumber()}" required />
                        <shared:validation-error field="floorNumber" />
                    </div>
                    <div class="form-group">
                        <label for="capacity" class="control-label">Capacity</label>
                        <input type="number" id="capacity" name="capacity" class="form-control" 
                               value="${unit.capacity()}" min="1" required />
                        <small class="text-muted">Number of residents this unit can accommodate</small>
                        <shared:validation-error field="capacity" />
                        
                        <c:if test="${unit.currentOccupancy() > 0}">
                            <div class="alert alert-info mt-2">
                                <small>This unit currently has ${unit.currentOccupancy()} resident(s)</small>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <div class="form-check d-flex align-items-center">
                            <input type="checkbox" id="active" name="active" value="true" class="form-check-input"
                                <c:if test="${unit.active()}">checked</c:if> />
                            <label class="form-check-label me-2" for="active">Active</label>
                            <shared:validation-error field="active" />
                        </div>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary mt-3">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>  
</manager:layout>