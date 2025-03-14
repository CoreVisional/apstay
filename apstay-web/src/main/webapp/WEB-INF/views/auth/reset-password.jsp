<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags/auth" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<auth:auth-template title="Reset Password">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="p-5">
                <div class="text-center">
                    <h4 class="text-gray-900 mb-2">Reset Your Password</h4>
                    <p class="text-gray-700 mb-4">Please enter your new password below.</p>
                </div>
                
                <shared:notice-template />
                
                <form class="user" action="${pageContext.request.contextPath}/reset-password" method="POST">
                    <input type="hidden" name="token" value="${token}" />
                    
                    <div class="form-group mb-4">
                        <label for="password" class="form-label">New Password</label>
                        <input type="password" class="form-control form-control-user"
                            id="password" name="password" required>
                        <shared:validation-error field="password" />
                    </div>
                    
                    <div class="form-group mb-4">
                        <label for="confirmPassword" class="form-label">Confirm New Password</label>
                        <input type="password" class="form-control form-control-user"
                            id="confirmPassword" name="confirmPassword" required>
                        <shared:validation-error field="confirmPassword" />
                    </div>
                    
                    <div class="form-group mt-4">
                        <button type="submit" class="btn btn-primary btn-user btn-block">
                            Reset Password
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</auth:auth-template>