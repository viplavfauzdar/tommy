<%@page import="com.financegeorgia.service.UserFacadeREST"%>
<%
UserFacadeREST uf = new UserFacadeREST();
if(uf.findByEmail1(request.getParameter("email")))
    out.println("false");
else
    out.println("true");
%>