<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<li class="nav-item dropdown no-arrow show">
    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
       data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
        <div>
            <div class="row">
                <span class="col-12 mr-2 d-none d-lg-inline text-gray-800 medium text-right">
                    ${user.fullName}
                </span>
                <span class="col-12 mr-2 d-none d-lg-inline text-gray-600 small text-right">
                    ${user.email}
                </span>
            </div>
        </div>
        <img class="img-profile rounded-circle ml-3" src="${pageContext.request.contextPath}/assets/images/user.png" alt="User Photo">
    </a>
    <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
        <c:set var="profileURL" value="${pageContext.request.contextPath}" />
        
        <c:choose>
            <c:when test="${user.hasRole('manager') || user.hasRole('superuser')}">
                <c:set var="profileURL" value="${pageContext.request.contextPath}/manager/profile" />
            </c:when>
            <c:when test="${user.hasRole('security')}">
                <c:set var="profileURL" value="${pageContext.request.contextPath}/security/profile" />
            </c:when>
            <c:otherwise>
                <c:set var="profileURL" value="${pageContext.request.contextPath}/resident/profile" />
            </c:otherwise>
        </c:choose>
        
        <a class="dropdown-item" href="${profileURL}">
            <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
            Profile
        </a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
            <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
            Logout
        </a>
    </div>
</li>