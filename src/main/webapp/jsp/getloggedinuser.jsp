<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.financegeorgia.service.UserFacadeREST"%>
<%
response.setContentType("application/json");
//is user Id is null in session then get it and put in session
if(session.getAttribute("userId")==null && request.getRemoteUser()!=null){
    (new UserFacadeREST()).getMe(request);
}
//check user role, if admin
out.print("{\"email\":\"" + request.getRemoteUser() + "\",\"role\":\"" + request.isUserInRole("admin") + "\"}");
%>