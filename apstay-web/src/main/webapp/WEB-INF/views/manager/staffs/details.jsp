<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="f" uri="/tlds/functions" %>

<manager:layout title="Staff Details">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h3 class="h3 mb-0 text-gray-800">Staff Details</h3>
    </div>

    <hr />

    <div>
        <dl class="row">
            <dt class="col-sm-2">Full Name</dt>
            <dd class="col-sm-10">${staff.getName()}</dd>

            <dt class="col-sm-2">Role</dt>
            <dd class="col-sm-10">
                <span class="badge badge-${staff.getRole() eq 'superuser' ? 'danger' : staff.getRole() eq 'manager' ? 'primary' : 'success'}">
                    ${staff.getRole()}
                </span>
            </dd>

            <dt class="col-sm-2">Identity Number</dt>
            <dd class="col-sm-10">${staff.getIdentityNumber()}</dd>

            <dt class="col-sm-2">Email Address</dt>
            <dd class="col-sm-10">${staff.getEmail()}</dd>

            <dt class="col-sm-2">Gender</dt>
            <dd class="col-sm-10">${staff.getGender()}</dd>

            <dt class="col-sm-2">Phone Number</dt>
            <dd class="col-sm-10">${staff.getPhone()}</dd>

            <dt class="col-sm-2">Address</dt>
            <dd class="col-sm-10">${staff.getAddress()}</dd>

            <c:if test="${isSecurityStaff}">
                <dt class="col-sm-2">Commute Mode</dt>
                <dd class="col-sm-10">${staff.getCommuteMode().toString()}</dd>
            </c:if>
        </dl>
    </div>
</manager:layout>