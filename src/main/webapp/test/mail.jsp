<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.financegeorgia.utils.Mailer"%>
<%
BufferedReader buf = new BufferedReader(new FileReader("C:\\Users\\vxf4071\\Google Drive\\NetBeansProjects\\tommy\\src\\main\\webapp\\test\\emailtemplate4.html"));
String msg = "";
String line = buf.readLine();
while(line != null){
    msg = msg + line;
    line = buf.readLine();
}
out.println(msg);

Mailer mailer = new Mailer();
mailer.send("smtp.gmail.com", "viplav.fauzdar@gmail.com", "S0mn@2013", "vfauzdar@financegeorgia.com", "viplav_fauzdar@homedepot.com", "Test", msg);
%>