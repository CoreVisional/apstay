<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>

<li class="nav-item ${activeNav == 'home' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/resident">
        <i class="fa-solid fa-home"></i>
        <span>Home</span>
    </a>
</li>

<li class="nav-item ${activeNav == 'visits' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/resident/visits">
        <i class="fa-solid fa-address-card"></i>
        <span>e-Visitor</span>
    </a>
</li>
