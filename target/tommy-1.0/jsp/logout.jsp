<%
    request.logout();
    session.invalidate();
    session = null;
    response.sendRedirect("/index.html");
%>