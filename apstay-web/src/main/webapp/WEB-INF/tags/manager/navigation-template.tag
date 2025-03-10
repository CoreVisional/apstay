<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>

<li class="nav-item ${activeNav == 'home' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/manager">
        <i class="fa-solid fa-home"></i>
        <span>Home</span>
    </a>
</li>

<li class="nav-item ${activeNav == 'registrations' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/manager/registrations">
        <i class="fa-regular fa-id-card"></i>
        <span>Registrations</span>
    </a>
</li>

<li class="nav-item ${activeNav == 'staffs' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/manager/staffs">
        <i class="fa-solid fa-person-military-pointing"></i>
        <span>Staffs</span>
    </a>
</li>

<li class="nav-item ${activeNav == 'residents' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/manager/residents">
        <i class="fa-solid fa-users"></i>
        <span>Residents</span>
    </a>
</li>

<li class="nav-item ${activeNav == 'units' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/manager/units">
        <i class="fa-solid fa-building"></i>
        <span>Units</span>
    </a>
</li>

<li class="nav-item ${activeNav == 'reports' ? 'active' : ''}">
    <a class="nav-link" href="${pageContext.request.contextPath}/manager/reports">
        <i class="fa-solid fa-file"></i>
        <span>Reports</span>
    </a>
</li>
