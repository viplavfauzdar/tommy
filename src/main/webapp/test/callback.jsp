
<%@page import="com.financegeorgia.echosign.app.DownloadDocumentsOfAgreement"%>
<%
System.out.println("Echosign callback: " + request.getRemoteAddr() + " " + request.getQueryString());

String documentKey = request.getParameter("documentKey");
String eventType = request.getParameter("eventType");
//download only once
if(eventType.equals("SIGNATURE_REQUESTED")){
    DownloadDocumentsOfAgreement da = new DownloadDocumentsOfAgreement();
    da.setAgreementId(documentKey);
    da.run();
}



/*
downloaded 4 copies because of 4 different eventType (CREATED,SIGNATURE_REQUESTED,EMAIL_VIEWED,ESIGNED)  - Filter on the last one or the second one 
created event throws null pointer

LOOK AT THE OPTION BELOW - it sends user back to URL
WidgetCompletionInfo {
deframe (boolean, optional): If deframe is false, the success page will be shown inside the widget frame. If deframe is true, the success page will be shown in the full browser window. Note that if this widget is loaded through javascript returned from this end point, browser security restrictions do not permit automatic redirection in the full browser window, so if deframe is true the user will instead just see a link to the success page. We recommend this scenario be avoided - in other words, setting deframe to false is recommended for this case.,
delay (integer, optional): The delay (in seconds) before the user is taken to the success page. If this value is greater than 0, the user will first see the standard EchoSign success message, and then after a delay will be redirected to your success page. Note that this parameter has no effect for widgets loaded with javascript when deframe is true.,
url (string): A publicly accessible url to which the user will be sent after successfully completing the widget. If the URL you provide includes information that allows you to identify the specific transaction, such as your own unique identifier, you can use the browser request to this URL as a callback to notify you that this transaction is completed. In addition, EchoSign will append a documentKey parameter to the URL which will contain the EchoSign DocumentKey for this signed widget, but only if the sender is the same as the API key user. Your application can use this value to get the form data for this widget.
*/

%>