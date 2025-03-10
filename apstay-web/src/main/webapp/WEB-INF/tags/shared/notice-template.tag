<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.notice}">
    <div class="alert ${sessionScope.noticeBg} alert-dismissible fade show mt-2" role="alert">
        ${sessionScope.notice}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <c:remove var="notice" scope="session"/>
    <c:remove var="noticeBg" scope="session"/>
</c:if>
