<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<manager:layout>
    <div class="container-fluid">
        <h3>Create Unit</h3>
        
        <hr />
        
        <shared:notice-template />
        
        <div class="row">
            <div class="col-md-4">
                <form action="${pageContext.request.contextPath}/manager/units/create" method="POST">
                    <div class="form-group">
                        <label for="unit-name" class="control-label">Unit Name</label>
                        <input type="text" id="unit-name" name="unit-name" class="form-control" required />
                        <shared:validation-error field="unitName" />
                    </div>
                    <div class="form-group">
                        <label for="floor-number" class="control-label">Floor Number</label>
                        <input type="number" id="floor-number" name="floor-number" class="form-control" required />
                        <shared:validation-error field="floorNumber" />
                    </div>
                    <div class="form-group">
                        <label for="capacity" class="control-label">Capacity</label>
                        <input type="number" id="capacity" name="capacity" class="form-control" value="1" min="1" required />
                        <small class="text-muted">Number of residents this unit can accommodate</small>
                        <shared:validation-error field="capacity" />
                    </div>
                    <div class="form-group">
                        <input type="submit" value="Submit" class="btn btn-primary" />
                    </div>
                </form>
            </div>
        </div>
    </div>  
</manager:layout>