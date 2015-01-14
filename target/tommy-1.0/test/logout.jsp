<%
    request.logout();
    session.invalidate();
    session = null;
    response.sendRedirect("login1.jsp");
%>