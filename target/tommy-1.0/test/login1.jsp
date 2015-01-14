<%    
    if (request.getParameter("user") != null) {        
        request.login(request.getParameter("user"), request.getParameter("pass"));
        //request.authenticate(response);
        out.println(request.getRemoteUser());
        out.println(request.getAuthType());        
    }
%>
<p><a href="/test/logout.jsp">Logout</a>

