<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="resident" tagdir="/WEB-INF/tags/resident" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="f" uri="/tlds/functions" %>

<resident:layout title="e-Visitor">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Visits</h1>
        <a href="${pageContext.request.contextPath}/resident/visits/create" class="d-sm-inline-block btn btn-primary shadow-sm">
            <i class="fa-solid fa-plus fa-fw"></i> New Request
        </a>
    </div>
    
    <shared:notice-template />

    <table class="table table-stripped table-show-dt">
        <thead>
            <tr>
                <th>Visitor Name</th>
                <th>Arrival Date</th>
                <th>Verification Code</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="visit" items="${visits}">
                <tr>
                    <td>${visit.name()}</td>
                    <td>${f:formatLocalDateTime(visit.arrivalDateTime(), 'MM/dd/yyyy hh:mm a')}</td>
                    <td>
                        <div class="d-flex align-items-center">
                            <c:choose>
                                <c:when test="${visit.active()}">
                                    <span>${visit.verificationCode()}</span>
                                    <button type="button" class="btn btn-link text-primary ml-2 p-0" 
                                            data-toggle="modal" data-target="#qrCodeModal" 
                                            data-code="${visit.verificationCode()}" 
                                            data-active="${visit.active()}"
                                            title="Show QR Code">
                                        <i class="fas fa-qrcode"></i>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <s title="Code is no longer active">
                                        ${visit.verificationCode()}
                                    </s>
                                    <button type="button" class="btn btn-link text-muted ml-2 p-0" disabled 
                                            title="Code is inactive">
                                        <i class="fas fa-qrcode"></i>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${visit.status() == 'SUBMITTED'}">
                                <span class="badge badge-info">Submitted</span>
                            </c:when>
                            <c:when test="${visit.status() == 'REACHED'}">
                                <span class="badge badge-success">Reached</span>
                            </c:when>
                            <c:when test="${visit.status() == 'CANCELLED'}">
                                <span class="badge badge-danger">Cancelled</span>
                            </c:when>
                            <c:when test="${visit.status() == 'CLOSED'}">
                                <span class="badge badge-secondary">Closed</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-secondary">${visit.status()}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/resident/visits/edit/${visit.id()}" class="btn btn-warning btn-sm">
                            <i class="fas fa-pen fa-sm fa-fw"></i> Edit
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty visits}">
                <tr>
                    <td colspan="4" class="text-center">No Visits Found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    
    <!-- QR Code Modal -->
    <div class="modal fade" id="qrCodeModal" tabindex="-1" role="dialog" aria-labelledby="qrCodeModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="qrCodeModalLabel">Verification QR Code</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                    <div id="qrCodeContainer" class="mb-3"></div>
                    <p>Verification Code: <span id="modalVerificationCode" class="font-weight-bold"></span></p>
                    <p class="text-muted small">Share this QR code with your visitor. They can show it to security when they arrive.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Verification Code Modal -->
    <c:if test="${not empty sessionScope.verificationCode}">
        <div class="modal fade" id="verificationCodeModal" tabindex="-1" role="dialog" aria-labelledby="verificationCodeModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="verificationCodeModalLabel">Verification Code</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Please share this verification code with your visitor:</p>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" id="verificationCodeInput" value="${sessionScope.verificationCode}" readonly>
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button" id="copyButton">
                                    <i class="fas fa-copy"></i> Copy
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <c:remove var="verificationCode" scope="session" />
    </c:if>
</resident:layout>