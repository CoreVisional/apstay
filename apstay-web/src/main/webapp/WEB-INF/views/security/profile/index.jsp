<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" tagdir="/WEB-INF/tags/security" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ page import="com.apu.apstay.enums.Gender" %>
<%@ page import="com.apu.apstay.enums.CommuteMode" %>

<security:layout>
    <div class="container-fluid">
        <h1 class="h3 mb-4 text-gray-800">My Profile</h1>
        
        <shared:notice-template />

        <div class="row">
            <div class="col-lg-6">
                <div class="card shadow mb-4">
                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary">Account Information</h6>
                        <button type="button" class="btn btn-sm btn-primary" id="editAccountBtn">
                            <i class="fas fa-edit fa-sm"></i>
                        </button>
                    </div>
                    <div class="card-body">
                        <form id="accountForm" method="POST" action="${pageContext.request.contextPath}/security/profile">
                            <input type="hidden" name="formType" value="account"> 
                            
                            <div class="form-group row">
                                <label for="username" class="col-sm-4 col-form-label">Username</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control account-field" id="username" name="username" value="${accountVM.username}" readonly>
                                    <shared:validation-error field="username" />
                                </div>
                            </div>
                            
                            <div class="form-group row">
                                <label for="email" class="col-sm-4 col-form-label">Email</label>
                                <div class="col-sm-8">
                                    <input type="email" class="form-control account-field" id="email" name="email" value="${accountVM.email}" readonly>
                                    <shared:validation-error field="email" />
                                </div>
                            </div>
                            
                            <div class="form-group row">
                                <div class="col-sm-12 text-right">
                                    <a href="${pageContext.request.contextPath}/security/profile/change-password" class="btn btn-warning">
                                        <i class="fas fa-key fa-sm"></i> Change Password
                                    </a>
                                </div>
                            </div>

                            <div class="form-group row d-none" id="accountButtons">
                                <div class="col-sm-12 text-right">
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                    <button type="button" class="btn btn-secondary" id="cancelAccountBtn">Cancel</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card shadow mb-4">
                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary">Personal Information</h6>
                        <button type="button" class="btn btn-sm btn-primary" id="editPersonalBtn">
                            <i class="fas fa-edit fa-sm"></i>
                        </button>
                    </div>
                    <div class="card-body">
                        <form id="personalForm" method="POST" action="${pageContext.request.contextPath}/security/profile">
                            <input type="hidden" name="formType" value="personal">
                            
                            <div class="form-group row">
                                <label for="name" class="col-sm-4 col-form-label">Full Name</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control personal-field" id="name" name="name" value="${profileVM.name}" readonly>
                                </div>
                            </div>
                            
                            <div class="form-group row">
                                <label for="identityNumber" class="col-sm-4 col-form-label">Identity Number</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control personal-field" id="identityNumber" name="identityNumber" value="${profileVM.identityNumber}" readonly>
                                </div>
                            </div>
                            
                            <div class="form-group row">
                                <label for="gender" class="col-sm-4 col-form-label">Gender</label>
                                <div class="col-sm-8">
                                    <select class="form-control personal-field" id="gender" name="gender" disabled>
                                        <c:forEach items="<%= Gender.values() %>" var="genderOption">
                                            <option value="${genderOption.name()}" <c:if test="${genderOption.name() eq profileVM.gender}">selected</c:if>>
                                                ${genderOption.toString()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group row">
                                <label for="phone" class="col-sm-4 col-form-label">Phone</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control personal-field" id="phone" name="phone" value="${profileVM.phone}" readonly>
                                </div>
                            </div>
                            
                            <div class="form-group row">
                                <label for="address" class="col-sm-4 col-form-label">Address</label>
                                <div class="col-sm-8">
                                    <textarea class="form-control personal-field" id="address" name="address" rows="3" readonly>${profileVM.address}</textarea>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="commuteMode" class="col-sm-4 col-form-label">Commute Mode</label>
                                <div class="col-sm-8">
                                    <select class="form-control personal-field" id="commuteMode" name="commuteMode" disabled>
                                        <c:forEach items="<%= CommuteMode.values() %>" var="commuteModeOption">
                                            <option value="${commuteModeOption.name()}" <c:if test="${commuteModeOption.name() eq profileVM.commuteMode}">selected</c:if>>
                                                ${commuteModeOption.toString()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row d-none" id="personalButtons">
                                <div class="col-sm-12 text-right">
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                    <button type="button" class="btn btn-secondary" id="cancelPersonalBtn">Cancel</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const editAccountBtn = document.getElementById('editAccountBtn');
            const cancelAccountBtn = document.getElementById('cancelAccountBtn');
            const accountFields = document.querySelectorAll('.account-field');
            const accountButtons = document.getElementById('accountButtons');

            // Check if we should be in account edit mode
            const editMode = "${editMode}";
            if (editMode === "account") {
                accountFields.forEach(field => {
                    field.readOnly = false;
                });
                accountButtons.classList.remove('d-none');
                editAccountBtn.classList.add('d-none');
            }

            editAccountBtn.addEventListener('click', function() {
                accountFields.forEach(field => {
                    field.readOnly = false;
                });
                accountButtons.classList.remove('d-none');
                editAccountBtn.classList.add('d-none');
            });

            cancelAccountBtn.addEventListener('click', function() {
                accountFields.forEach(field => {
                    field.readOnly = true;
                });
                accountButtons.classList.add('d-none');
                editAccountBtn.classList.remove('d-none');
            });

            const editPersonalBtn = document.getElementById('editPersonalBtn');
            const cancelPersonalBtn = document.getElementById('cancelPersonalBtn');
            const personalFields = document.querySelectorAll('.personal-field');
            const personalButtons = document.getElementById('personalButtons');

            // Check if we should be in personal edit mode
            if (editMode === "personal") {
                personalFields.forEach(field => {
                    if (field.tagName === 'SELECT') {
                        field.disabled = false;
                    } else {
                        field.readOnly = false;
                    }
                });
                personalButtons.classList.remove('d-none');
                editPersonalBtn.classList.add('d-none');
            }

            editPersonalBtn.addEventListener('click', function() {
                personalFields.forEach(field => {
                    if (field.tagName === 'SELECT') {
                        field.disabled = false;
                    } else {
                        field.readOnly = false;
                    }
                });
                personalButtons.classList.remove('d-none');
                editPersonalBtn.classList.add('d-none');
            });

            cancelPersonalBtn.addEventListener('click', function() {
                personalFields.forEach(field => {
                    if (field.tagName === 'SELECT') {
                        field.disabled = true;
                    } else {
                        field.readOnly = true;
                    }
                });
                personalButtons.classList.add('d-none');
                editPersonalBtn.classList.remove('d-none');
            });
        });
    </script>
</security:layout>