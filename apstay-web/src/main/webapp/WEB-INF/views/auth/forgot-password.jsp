<%@ taglib prefix="auth" tagdir="/WEB-INF/tags/auth" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<auth:auth-template title="Forgot Password">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="p-5">
                <div class="d-flex justify-content-center align-items-center mb-5">
                    <a href="${pageContext.request.contextPath}/login" class="text-decoration-none mr-4">
                        <i class="fas fa-arrow-left"></i>
                    </a>
                    <h4 class="h4 text-gray-900 mb-0">Forgot Password?</h4>
                </div>
                
                <shared:notice-template />
                
                <form class="user" action="${pageContext.request.contextPath}/forgot-password" method="POST">
                    <div class="form-group mb-4">
                        <label for="email" class="form-label">Email Address</label>
                        <input type="email" class="form-control"
                            id="email" name="email" required>
                        <shared:validation-error field="email" />
                    </div>
                    
                    <div class="form-group mt-4 text-center">
                        <button type="submit" class="btn btn-primary">
                            Send Reset Link
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</auth:auth-template>