<%-- 
    Document   : redirect
    Created on : Dec 3, 2014, 5:31:35 PM
    Author     : VXF4071
--%>


<%@page import="com.financegeorgia.echosign.app.DownloadDocumentsOfAgreement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Echosign Redirect</title>
    </head>
    <body>
        <h1>Echosign redirected to this page after successful document sign!!</h1>
        <%
            String documentKey = request.getParameter("documentKey");
            out.print("Document Key: " + documentKey);
            DownloadDocumentsOfAgreement da = new DownloadDocumentsOfAgreement();
            da.setAgreementId(documentKey);
            da.run();
        %>
        <h2>A copy of the document has been downloaded successfully!!</h2>
    </body>
</html>
