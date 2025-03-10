<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.apu.apstay.enums.Gender" %>

<manager:layout>
    <div class="container-fluid">
        <h3>Create Staff</h3>
        <hr />
        <shared:notice-template />
        <form action="${pageContext.request.contextPath}/manager/staffs/create" method="POST">
            
            <!-- Card 1: Basic Information -->
            <div class="card mb-4">
                <div class="card-header">
                    <strong>Basic Information</strong>
                </div>
                <div class="card-body">
                    <!-- Username Field -->
                    <div class="form-group">
                        <label for="username" class="control-label">Username</label>
                        <input type="text" id="username" name="username" class="form-control" placeholder="Enter username" required />
                        <shared:validation-error field="username" />
                    </div>
                    
                    <!-- Email Field -->
                    <div class="form-group">
                        <label for="email" class="control-label">Email</label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="Enter email address" required />
                        <shared:validation-error field="email" />
                    </div>
                    
                    <!-- Role Dropdown -->
                    <div class="form-group">
                        <label for="role" class="control-label">Role</label>
                        <select id="role" name="role" class="form-control" required>
                            <option value="">Select Role</option>
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}">${role.name}</option>
                            </c:forEach>
                        </select>
                        <shared:validation-error field="role" />
                    </div>
                </div>
            </div>
            
            <!-- Card 2: Personal Details -->
            <div class="card mb-4">
                <div class="card-header">
                    <strong>Personal Details</strong>
                </div>
                <div class="card-body">
                    <!-- Full Name Field -->
                    <div class="form-group">
                        <label for="full-name" class="control-label">Full Name</label>
                        <input type="text" id="full-name" name="full-name" class="form-control" placeholder="Enter full name" required />
                        <shared:validation-error field="fullName" />
                    </div>
                    
                    <!-- Identity Number Field -->
                    <div class="form-group">
                        <label for="identity-number" class="control-label">Identity Number</label>
                        <input type="text" id="identity-number" name="identity-number" class="form-control" placeholder="Enter identity number" required />
                        <shared:validation-error field="identityNumber" />
                    </div>
                    
                    <!-- Gender Dropdown -->
                    <div class="form-group">
                        <label for="gender" class="control-label">Gender</label>
                        <select id="gender" name="gender" class="form-control" required>
                            <option value="">Select Gender</option>
                            <c:forEach items="<%= Gender.values() %>" var="genderOption">
                                <option value="${genderOption.name()}">${genderOption.toString()}</option>
                            </c:forEach>
                        </select>
                        <shared:validation-error field="gender" />
                    </div>
                </div>
            </div>
            
            <!-- Card 3: Contact Information -->
            <div class="card mb-4">
                <div class="card-header">
                    <strong>Contact Information</strong>
                </div>
                <div class="card-body">
                    <!-- Phone Field -->
                    <div class="form-group">
                        <label for="phone" class="control-label">Phone</label>
                        <input type="tel" id="phone" name="phone" class="form-control" pattern="[0-9]{10}" placeholder="012-3456789" required />
                        <shared:validation-error field="phone" />
                    </div>
                    
                    <!-- Address Field -->
                    <div class="form-group">
                        <label for="address" class="control-label">Address</label>
                        <textarea id="address" name="address" class="form-control" rows="3" placeholder="Enter address" required></textarea>
                        <shared:validation-error field="address" />
                    </div>
                </div>
            </div>
            
            <!-- Submit Button -->
            <div class="form-group">
                <input type="submit" value="Create" class="btn btn-primary" />
            </div>
        </form>
    </div>
</manager:layout>
