<%-- 
    Document   : paths
    Created on : Dec 3, 2014, 12:23:42 PM
    Author     : VXF4071
--%>

<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PATHS</title>
    </head>
    <body>
        <h1>PATHS:</h1>
        <%
            out.println(getClass().getClassLoader().getResource("/echosign/api/rest/sample/requests/Auth.json"));
            out.println(ClassLoader.getSystemResource("/echosign/api/rest/sample/requests/Auth.json"));
            out.println(getServletContext().getResource("/WEB-INF/classes/echosign/api/rest/sample/requests/Auth.json"));
            String file = getServletContext().getRealPath("/");
            out.println(file);
            file = file.replaceAll("\\\\", "/");
            out.println(file);
            file = file.substring(0, file.lastIndexOf("/")) + "/data";///myfile.txt";
            File dir = new File(file);
            dir.mkdir();
            out.println(file);
            java.io.FileWriter fw = new java.io.FileWriter(file + "/myfile.txt");
            fw.write("Hello Viplav");
            fw.close();
            
            String userhomedir = System.getProperty("user.home");
            String userworkdir = System.getProperty("user.dir");
            String filesep = System.getProperty("file.separator");
            
            out.println(userhomedir);
            out.println(userworkdir);
            out.println(filesep);
            
            dir = new File(userhomedir + filesep + "data");
            dir.mkdir();
            fw = new java.io.FileWriter(userhomedir + filesep + "data" + filesep + "myfile.txt");
            fw.write("Hello Viplav");
            fw.close();
            
            
        %>        
    </body>
</html>
