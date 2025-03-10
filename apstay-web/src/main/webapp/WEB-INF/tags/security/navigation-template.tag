<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>

<li class="nav-item ${activeNav == 'home' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/security">
        <i class="fa-solid fa-home"></i>
        <span>Home</span>
    </a>
</li>
