<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags/auth" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="security" tagdir="/WEB-INF/tags/security" %>
<%@ taglib prefix="resident" tagdir="/WEB-INF/tags/resident" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>

<c:choose>
    <c:when test="${user.hasRole('manager') || user.hasRole('superuser')}">
        <manager:layout>
            <div class="container-fluid">
                <h3>Change Password</h3>
                <hr />
                <shared:notice-template />
                <auth:change-password-form />
            </div>
        </manager:layout>
    </c:when>
    <c:when test="${user.hasRole('security')}">
        <security:layout>
            <div class="container-fluid">
                <h3>Change Password</h3>
                <hr />
                <shared:notice-template />
                <auth:change-password-form />
            </div>
        </security:layout>
    </c:when>
    <c:otherwise>
        <resident:layout>
            <div class="container-fluid">
                <h3>Change Password</h3>
                <hr />
                <shared:notice-template />
                <auth:change-password-form />
            </div>
        </resident:layout>
    </c:otherwise>
</c:choose>