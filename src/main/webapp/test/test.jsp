<%@page import="com.financegeorgia.echosign.exec.EchoSignRSClient"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="com.financegeorgia.utils.SynapsePayRSClient"%>
<%
//    String pl = "{\"email\": \"sdda454@asdasd.com\",   \"fullname\": \"asdasd\",    \"phonenumber\": \"6782211212\",    \"ip_address\": \"192.168.1.101\",    \"client_id\": \"Ef2aeSO57wWssMP7NZz4\",    \"client_secret\": \"oZslaR5KPu6gEI57DOw5miHklKRJNWhqdEAlBu5I\"}";
//    JSONObject obj = (JSONObject)new JSONParser().parse(pl);
//    //out.println(SynapsePayRSClient.getInstance().createAccount1(obj).toJSONString());
//    out.println(SynapsePayRSClient.getInstance().refresh("UDSpvEYql2zg8MNMdw8uoiDLMTe5bTULf96uTME7").toJSONString());
//    
//    request.getRequestDispatcher(string)
    
    out.println(request.getLocalAddr());
    out.println(request.getRemoteAddr());
    out.println(request.getRequestURL());
    
    out.println(new EchoSignRSClient().uploadDocument());//.refreshOauthToken();
%>

<a href="/Uploader?action=stream&folder=69&filename=checklist_accinvestor.pdf">click</a>
<P></P>
<a href="/doc/checklist_accinvestor.pdf">and again</a>