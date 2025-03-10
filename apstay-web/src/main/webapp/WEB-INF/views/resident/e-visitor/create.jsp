<%@ taglib prefix="resident" tagdir="/WEB-INF/tags/resident" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.apu.apstay.enums.Gender" %>

<resident:layout>
    <div class="container-fluid">
        <h3>New Visit Request</h3>
        
        <hr />
        
        <shared:notice-template />
        
        <div class="row">
            <div class="col-md-4">
                <form action="${pageContext.request.contextPath}/resident/visits/create" method="POST">
                    <div class="form-group">
                        <label for="name" class="control-label">Name</label>
                        <input type="text" id="name" name="name" class="form-control" required />
                        <shared:validation-error field="name" />
                    </div>

                    <div class="form-group">
                        <label for="identity-number" class="control-label">Identity Number</label>
                        <input type="text" id="identity-number" name="identity-number" class="form-control" required />
                        <shared:validation-error field="identityNumber" />
                    </div>

                    <div class="form-group">
                        <label for="gender" class="control-label">Gender</label>
                        <select class="form-control" id="gender" name="gender" required>
                            <option value="">Select Gender</option>
                            <c:forEach items="<%= Gender.values() %>" var="genderOption">
                                <option value="${genderOption.name()}">${genderOption.toString()}</option>
                            </c:forEach>
                        </select>
                        <shared:validation-error field="gender" />
                    </div>

                    <div class="form-group">
                        <label for="phone" class="control-label">Phone Number</label>
                        <input type="tel" id="phone" name="phone" class="form-control" required />
                        <shared:validation-error field="phone" />
                    </div>

                    <div class="form-group">
                        <label for="arrival-date-time" class="control-label">Arrival Date & Time</label>
                        <input type="datetime-local" id="arrival-date-time" name="arrivalDateTime" class="form-control" required />
                        <shared:validation-error field="arrivalDateTime" />
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-primary mt-3">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>  
</resident:layout>