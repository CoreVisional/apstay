<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="security" tagdir="/WEB-INF/tags/security" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
    int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageContext.request.contextPath} - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lib/fontawesome/css/all.min.css" />
    <link rel="stylesheet" href="https://static2.sharepointonline.com/files/fabric/office-ui-fabric-core/11.0.0/css/fabric.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lib/sb-admin-2/css/sb-admin-2.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/site.css" />
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap4.min.css">
</head>
<body id="page-top">
    <div id="wrapper">
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}">
                <div class="sidebar-brand-icon">
                    <img src="${pageContext.request.contextPath}/assets/images/logo.png" height="30" width="30" alt="Icon"/>
                </div>
                <div class="sidebar-brand-text mx-1" style="font-weight: normal;">
                    APStay | Security
                </div>
            </a>
            <hr class="sidebar-divider d-none d-md-block">
            <security:navigation-template />
        </ul>

        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>
                    <div class="sidebar-brand-text mx-1 d-md-none" style="font-weight: normal;">Admin</div>
                    <ul class="navbar-nav ml-auto">
                        <shared:topbar />
                    </ul>
                </nav>

                <div class="container-fluid">
                    <jsp:doBody />
                </div>
            </div>

            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        &copy; <%= currentYear %> - APStay - <a href="${pageContext.request.contextPath}/privacy">Privacy</a>
                    </div>
                </div>
            </footer>
        </div>
    </div>

    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/js/sb-admin-2.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/chart.js/Chart.min.js"></script>
    <script type="module" src="${pageContext.request.contextPath}/assets/js/site.js" defer></script>
</body>
</html>
