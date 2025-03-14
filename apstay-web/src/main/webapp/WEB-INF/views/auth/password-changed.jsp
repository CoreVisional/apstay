<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags/auth" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<auth:auth-template title="Password Changed">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="p-5">
                <div class="text-center mb-4">
                    <i class="fas fa-check-circle text-success fa-4x mb-3"></i>
                    <h3 class="text-gray-900">Password Reset Successful</h3>
                    <p class="text-gray-700 mt-3">Your password has been changed successfully.</p>
                    <p class="text-gray-700">You can now log in with your new password.</p>
                </div>
                
                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-user btn-block">
                        Login
                    </a>
                </div>
            </div>
        </div>
    </div>
</auth:auth-template>