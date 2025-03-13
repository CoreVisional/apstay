<%@ tag language="java" body-content="scriptless" %>
<%@ attribute name="title" required="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>${title} - APStay</title>
    <link href="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/lib/sb-admin-2/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body class="bg-gradient-primary">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-10 col-lg-12 col-md-9">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="login-wrapper">
                            <div class="row justify-content-center">
                                <div class="col">
                                    <div class="p-5">
                                        <jsp:doBody/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/sb-admin-2/js/sb-admin-2.min.js"></script>
</body>
</html>
