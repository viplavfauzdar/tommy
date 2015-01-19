<%@page import="com.financegeorgia.service.UserFacadeREST"%>
<%=request.getRemoteUser()%>
<%if(session.getAttribute("userId")==null && request.getRemoteUser()!=null){
    (new UserFacadeREST()).getMe(request);
}%>