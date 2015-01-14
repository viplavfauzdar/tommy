<%@page import="com.financegeorgia.echosign.app.CreateNewWidgetWithCounerSigners"%><%
String fileToBeUploaded = request.getParameter("fileToBeUploaded");
CreateNewWidgetWithCounerSigners cw = new CreateNewWidgetWithCounerSigners();
if(fileToBeUploaded!=null) cw.setfileToBeUploaded(fileToBeUploaded);
cw.run();
%>
<!--<iframe src="<%//=cw.getWidgetURL()%>" id="ifrmLegal" style='border: 1px solid black; width: 800px; height: 800px'></iframe>-->
<%=cw.getWidgetJS()%>


