<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>

<div class="row">
    <div class="col-md-4">
        <form action="${pageContext.request.contextPath}${formAction}" method="POST">
            <div class="form-group">
                <label for="currentPassword">Current Password</label>
                <input type="password" id="currentPassword" name="currentPassword" class="form-control" required />
                <shared:validation-error field="currentPassword" />
            </div>

            <div class="form-group">
                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" class="form-control" required />
                <shared:validation-error field="newPassword" />
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm New Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required />
                <shared:validation-error field="confirmPassword" />
            </div>

            <div class="form-group mt-3">
                <button type="submit" class="btn btn-primary">Update</button>
                <a href="${pageContext.request.contextPath}${backUrl}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>