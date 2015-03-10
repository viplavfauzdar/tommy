<%@page import="com.financegeorgia.service.UserFacadeREST"%>
<%
UserFacadeREST uf = new UserFacadeREST();
if(uf.findByEmail1(request.getParameter("email")))
    out.println("Already taken. Please use another one.");
else
    out.println("good to go");
%>