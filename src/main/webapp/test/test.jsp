<%@page import="com.financegeorgia.entities.ArmorAccount"%>
<%@page import="com.financegeorgia.utils.ArmorPayments"%>
<%@page import="com.owlike.genson.Genson"%>
<%@page import="javax.ws.rs.core.Response"%>
<%@page import="javax.ws.rs.client.Invocation"%>
<%@page import="javax.ws.rs.client.WebTarget"%>
<%@page import="javax.ws.rs.client.ClientBuilder"%>
<%@page import="javax.ws.rs.client.Client"%>
<%@page import="com.financegeorgia.utils.SaltedHash"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@page import="com.financegeorgia.entities.Business"%>
<%@page import="com.financegeorgia.service.BusinessFacadeREST"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%
//        FileInputStream fin = null;
//        OutputStream out1 = null;
//        File file = new File("/var/lib/openshift/549e56b25973cab96c000042/app-root/data/FG/42/execSumm.pdf");
//        if (!file.exists()) {
//            System.out.println("Sending placeholder image");
//            try {
//                //file = new File("/img/placeholder.png");
//                response.sendRedirect("/img/placeholder.png");
//            } catch (IOException ex) {
//                System.out.println(ex);
//            }
//        } else {
//            try {
//                fin = new FileInputStream(file);
//                out1 = response.getOutputStream();
//                int read = 0;
//                byte[] bytes = new byte[1024];
//                while ((read = fin.read(bytes)) != -1) {
//                    out1.write(bytes, 0, read);
//                }
//            } catch (IOException ex) {
//                System.out.println(ex);
//            } finally {
//                try {
//                    if (fin != null) {
//                        fin.close();
//                    }
//                    if (out1 != null) {
//                        out1.close();
//                    }
//                } catch (IOException ex) {
//                    System.out.println(ex);
//                }
//            }
//        }

    //out.println("profilePic1.JPG".split("\\.").length);
//    BusinessFacadeREST bf = new BusinessFacadeREST();
//    Business bus = bf.findNyName(request.getParameter("name"));
//    //response.sendRedirect("/businessprofile.html#/" + bus.getId() + "#" + bus.getUserId());
//    request.getRequestDispatcher("/businessprofile.html#/" + bus.getId() + "#" + bus.getUserId()).forward(request, response);
    //armor payments
    //auth
//    String apiKey = "73066f8041cc3d5a9ad7e2a3b9823416";
//    String apiSecret = "ca9fd3568f4fb0055255d0cce1b24c515250426b75aadb2bd893be5d1dc57d7f";    
//    //TimeZone tz = TimeZone.getTimeZone("UTC");
//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//    //df.setTimeZone(tz);
//    String nowAsISO = df.format(new Date());
//    out.println(nowAsISO);
//    String string_to_sign = apiSecret + ":GET:/accounts:" + nowAsISO;
//    out.println(string_to_sign);
//    SaltedHash sh = SaltedHash.getInstance();
//    String signature = sh.get_SHA_512_SecurePassword(string_to_sign);
//    out.println(signature);
//    String headers = "X_ARMORPAYMENTS_APIKEY:" + apiKey +
//    "<br>X_ARMORPAYMENTS_REQUESTTIMESTAMP:" + nowAsISO +
//    "<br>X_ARMORPAYMENTS_SIGNATURE:" + signature;
//    out.println("<p>" + headers);
//    
//    Client client = ClientBuilder.newClient();
//    WebTarget target = client.target("https://sandbox.armorpayments.com/accounts");
//    Invocation.Builder invocationBuilder = target.request();
//    invocationBuilder.header("X_ARMORPAYMENTS_APIKEY", apiKey);
//    invocationBuilder.header("X_ARMORPAYMENTS_REQUESTTIMESTAMP", nowAsISO);
//    invocationBuilder.header("X_ARMORPAYMENTS_SIGNATURE", signature);
//    Response resp = invocationBuilder.get();
//    out.println("<br>" + resp.getStatus());
//    out.println("<br>" + resp.readEntity(String.class));
    ArmorPayments ap = ArmorPayments.getInstance();
    ArmorAccount aa = new ArmorAccount();
    aa.setUser_name("Viplav1");
    aa.setUser_email("vip2@fauz.com");
    aa.setUser_phone("6752211212");
    aa.setAddress("1234 Elm St.");
    aa.setCity("Atlanta");
    aa.setCompany("Fauzdar Inc");
    aa.setAgreed_terms("true");
    aa.setEmail_confirmed("false");
    //ap.createAccount(aa);
    ap.getAllAccounts();
    ap.getOneAccount("150528171314");
    ap.getUserInAccount("150528196352");
    ap.getOrders("150528194535");
    //ap.createOrder("150528194535");
    
    ap.getPaymentWidget("150528194535", "150528197586", "150528237181");
%>