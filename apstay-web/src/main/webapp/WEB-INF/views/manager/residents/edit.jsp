<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.apu.apstay.enums.Gender" %>

<manager:layout>
    <div class="container-fluid">
        <h3>Edit Resident</h3>
        <hr />
        <shared:notice-template />
        <form action="${pageContext.request.contextPath}/manager/residents/edit/${resident.id()}" method="POST">

            <div class="card mb-4">
                <div class="card-header">
                    <strong>Account Information</strong>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <label for="username" class="control-label">Username</label>
                        <input type="text" id="username" name="username" class="form-control" value="${resident.username()}" required />
                        <shared:validation-error field="username" />
                    </div>

                    <div class="form-group">
                        <label for="email" class="control-label">Email</label>
                        <input type="email" id="email" name="email" class="form-control" value="${resident.email()}" required />
                        <shared:validation-error field="email" />
                    </div>
                </div>
            </div>

            <div class="card mb-4">
                <div class="card-header">
                    <strong>Unit Assignment</strong>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <label for="unitId" class="control-label">Assigned Unit</label>
                        <select id="unitId" name="unitId" class="form-control" required>
                            <option value="${resident.currentUnitId()}" selected>${resident.currentUnit()} (Current)</option>
                            <c:forEach items="${resident.availableUnits()}" var="unit">
                                <c:if test="${unit.id() != resident.currentUnitId()}">
                                    <option value="${unit.id()}">${unit.unitName()}-${unit.floorNumber()} (${unit.currentOccupancy()}/${unit.capacity()})</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <small class="mt-2 form-text text-muted">Select a different unit to reassign this resident.</small>
                        <shared:validation-error field="unitId" />
                    </div>
                </div>
            </div>

            <div class="card mb-4">
                <div class="card-header">
                    <strong>Personal Details</strong>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <label for="fullName" class="control-label">Full Name</label>
                        <input type="text" id="fullName" name="fullName" class="form-control" value="${resident.fullName()}" required />
                        <shared:validation-error field="fullName" />
                    </div>

                    <div class="form-group">
                        <label for="identityNumber" class="control-label">Identity Number</label>
                        <input type="text" id="identityNumber" name="identityNumber" class="form-control" value="${resident.identityNumber()}" required />
                        <shared:validation-error field="identityNumber" />
                    </div>

                    <div class="form-group">
                        <label for="gender" class="control-label">Gender</label>
                        <select id="gender" name="gender" class="form-control" required>
                            <c:forEach items="<%= Gender.values() %>" var="genderOption">
                                <option value="${genderOption.name()}" <c:if test="${genderOption.name() eq resident.gender()}">selected</c:if>>
                                    ${genderOption.toString()}
                                </option>
                            </c:forEach>
                        </select>
                        <shared:validation-error field="gender" />
                    </div>
                </div>
            </div>

            <div class="card mb-4">
                <div class="card-header">
                    <strong>Contact Information</strong>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <label for="phone" class="control-label">Phone</label>
                        <input type="tel" id="phone" name="phone" class="form-control" pattern="[0-9]{10}" value="${resident.phone()}" required />
                        <shared:validation-error field="phone" />
                    </div>

                    <div class="form-group">
                        <label for="address" class="control-label">Address</label>
                        <textarea id="address" name="address" class="form-control" rows="3" required>${resident.address()}</textarea>
                        <shared:validation-error field="address" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
    </div>
</manager:layout>