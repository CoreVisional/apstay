<% 
    // Redirect to /login, which will be handled by the LoginServlet
    response.sendRedirect(request.getContextPath() + "/login"); 
%>