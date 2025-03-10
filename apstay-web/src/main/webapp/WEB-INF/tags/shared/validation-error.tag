<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="field" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errors[field]}">
    <span class="text-danger small">${errors[field]}</span>
</c:if>