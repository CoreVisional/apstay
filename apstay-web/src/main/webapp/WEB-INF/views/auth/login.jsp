<%@ taglib prefix="login" tagdir="/WEB-INF/tags/auth" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<login:auth-template title="APStay - Login">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="p-5">
                <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Login</h1>
                </div>
                <shared:notice-template />
                <form class="user" action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="form-group">
                        <input type="text" class="form-control form-control-user"
                            id="login-key" name="login-key" aria-describedby="loginHelp"
                            placeholder="Username / Email Address" required>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control form-control-user"
                            id="password" name="password" placeholder="Password" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-user btn-block">
                        Login
                    </button>
                </form>
                <hr>
                <div class="text-center">
                    <a class="small" href="${pageContext.request.contextPath}/views/auth/forgot-password.jsp">Forgot Password?</a>
                </div>
                <div class="text-center">
                    <a class="small" href="${pageContext.request.contextPath}/register">Register an Account!</a>
                </div>
            </div>
        </div>
    </div>
</login:auth-template>
