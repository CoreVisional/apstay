<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" tagdir="/WEB-INF/tags/security" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>

<security:layout>
    <div class="container-fluid">

        <shared:notice-template />

        <div class="row">
            <div class="col-lg-8 mx-auto">
                <div class="text-center mb-4">
                    <h5 class="mt-3">Visitor Verification Code</h5>
                </div>
                <form id="verificationForm" action="${pageContext.request.contextPath}/security" method="POST" class="mt-4">
                    <div class="form-group">
                        <div class="d-flex justify-content-center mb-3">
                            <input type="text" id="digit1" class="form-control verification-digit text-center font-weight-bold mx-1" maxlength="1" pattern="[0-9]" inputmode="numeric" autofocus>
                            <input type="text" id="digit2" class="form-control verification-digit text-center font-weight-bold mx-1" maxlength="1" pattern="[0-9]" inputmode="numeric">
                            <input type="text" id="digit3" class="form-control verification-digit text-center font-weight-bold mx-1" maxlength="1" pattern="[0-9]" inputmode="numeric">
                            <input type="text" id="digit4" class="form-control verification-digit text-center font-weight-bold mx-1" maxlength="1" pattern="[0-9]" inputmode="numeric">
                            <input type="text" id="digit5" class="form-control verification-digit text-center font-weight-bold mx-1" maxlength="1" pattern="[0-9]" inputmode="numeric">
                            <input type="text" id="digit6" class="form-control verification-digit text-center font-weight-bold mx-1" maxlength="1" pattern="[0-9]" inputmode="numeric">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <c:if test="${not empty sessionScope.errorModal}">
        <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title" id="errorModalLabel">Verification Error</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>${sessionScope.errorMessage}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <c:remove var="errorModal" scope="session" />
        <c:remove var="errorMessage" scope="session" />
    </c:if>
</security:layout>