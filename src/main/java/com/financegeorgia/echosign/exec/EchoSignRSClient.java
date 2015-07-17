/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.echosign.exec;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author viplav
 */
public class EchoSignRSClient {

    private static Logger logger = Logger.getLogger(EchoSignRSClient.class);

    private static String clientId = "DN7STIL464D24D", clientSecret = "nkzi7lX2okreO6lqdYR23o0WwVXjPHkB";

    private static String web_access_point = "https://secure.echosign.com";
    private static String api_access_point = "https://api.echosign.com/api/rest/v3";

    private static String REFRESH_TOKEN = "2AAABLblqZhA8pOKKaxyiS7p-6L_n3rEBr2nqAsPQPSkKlHzkPswv8_eBzb87UMxXX77P4PUKZCk*";
    private static String OAUTH_TOKEN;

    private static Client client = ClientBuilder.newClient();
    private static Invocation.Builder invocationBuilder = null;

    private static Gson gson = new Gson();

    public String refreshOauthToken() {
        String pl = "refresh_token=" + REFRESH_TOKEN + "&client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=refresh_token";
        return send(pl, web_access_point + "/oauth/refresh");
    }

    private String getOauthToken() {
        return null;
    }

    public String send(String pl, String uri) {
        logger.debug("Payload: " + pl);
        WebTarget target = client.target(uri);
        invocationBuilder = target.request();

        Response resp = invocationBuilder.post(Entity.entity(pl, MediaType.APPLICATION_FORM_URLENCODED));
        logger.debug(resp.getStatus());
        String ret = resp.readEntity(String.class);
        JsonObject map = gson.fromJson(ret, JsonObject.class);
        logger.debug("Response: " + map);
        resp.close();
        return map.get("access_token").toString();
    }

    public String uploadDocument1() throws FileNotFoundException, MessagingException {

//        //FileInputStream fs = new FileInputStream("C:\\checklist_accinvestor.docx");
//        //FileDataBodyPart
        //InputStream stream = getClass().getClassLoader().getResourceAsStream("C:\\checklist_accinvestor.docx");
        //FormDataMultiPart fmp = new FormDataMultiPart();//.field("file", stream, MediaType.MULTIPART_FORM_DATA_TYPE);
        //fmp.bodyPart(new FileDataBodyPart("file",new File("C:\\checklist_accinvestor.docx")), MediaType.APPLICATION_OCTET_STREAM_TYPE);
//        //logger.debug("Payload: " + pl);
//        Multipart mp = new MimeMultipart();
//        BodyPart bp = new MimeBodyPart();
//        FileDataSource fileDataSource=new FileDataSource("C:\\checklist_accinvestor.docx");
//        bp.setDataHandler(new DataHandler(fileDataSource));
//        mp.addBodyPart(bp);
        //client.register(MultiPartFeature.class);
        WebTarget target = client.target(api_access_point + "/transientDocuments");
        invocationBuilder = target.request();
        invocationBuilder.header("Access-Token", refreshOauthToken());
//                //.header("Content-Disposition", "attachment; filename = checklist_accinvestor.docx");
//
        Response resp = invocationBuilder.post(Entity.entity(convertFileToJSON("C:/checklist_accinvestor.docx"), MediaType.APPLICATION_JSON));
        logger.debug(resp.getStatus());
        String ret = resp.readEntity(String.class);
        logger.debug("Response: " + ret);
//        //JsonObject map = gson.fromJson(ret, JsonObject.class);
//        resp.close();
        return null;//ret;//map.get("transientDocumentId").toString();
    }

    public static JsonObject convertFileToJSON(String fileName) {

        // Read from File to String
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (Exception e) {

        }

        return jsonObject;
    }

    public String uploadDocument2() {

        String charset = "UTF-8";
        File uploadFile1 = new File("C:/checklist_accinvestor.docx");
        //File uploadFile2 = new File("e:/Test/PIC2.JPG");
        String requestURL = "https://api.echosign.com/api/rest/v3/transientDocuments";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addHeaderField("Access-Token", refreshOauthToken());

//            multipart.addFormField("description", "Cool Pictures");
//            multipart.addFormField("keywords", "Java,upload,Spring");
            multipart.addFilePart("fileUpload", uploadFile1);

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public String uploadDocument() throws IOException {
        // Create header list for the request.
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(RestApiUtils.HttpHeaderField.ACCESS_TOKEN.toString(), refreshOauthToken());

        // Meta-data associated with the file.
        HashMap<String, String> fileMetaData = new HashMap<String, String>();
        fileMetaData.put(RestApiUtils.HttpHeaderField.MIME_TYPE.toString(), RestApiUtils.MimeType.PDF.toString());
        fileMetaData.put(RestApiUtils.HttpHeaderField.FILE_NAME.toString(), "checklist");

        JsonObject response = (JsonObject) RestApiUtils.makeApiCall(web_access_point + "/transientDocuments", headers, fileMetaData, new File("C:/checklist_accinvestor.pdf"));
        logger.debug("Upload Response: " + response);
        return null;
    }

//    public static JSONObject postTransientDocument(String accessToken, String mimeType, String fileToBeUploaded, String uploadedFileName) throws Exception {
//        // URL to invoke the transient documents end point.
//        String url = RestApiUtils.getBaseURIForAPI(accessToken) + TRANSIENT_DOCUMENTS_ENDPOINT;
//
//        // Create header list for the request.
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put(RestApiUtils.HttpHeaderField.ACCESS_TOKEN.toString(), refreshOauthToken());
//
//        // Meta-data associated with the file.
//        HashMap<String, String> fileMetaData = new HashMap<String, String>();
//        fileMetaData.put(RestApiUtils.HttpHeaderField.MIME_TYPE.toString(), RestApiUtils.MimeType.PDF.toString());
//        fileMetaData.put(RestApiUtils.HttpHeaderField.FILE_NAME.toString(), uploadedFileName);
//
//        // Get a reference to the file to be uploaded to EchoSign.
//        String pathToFile = FileUtils.REQUEST_PATH + fileToBeUploaded;
//        URL fileUrl = ClassLoader.getSystemResource(pathToFile);
//        File fileToUpload = new File(fileUrl.getPath());
//
//        // Invoke API and get JSON response
//        JSONObject responseJson = (JSONObject) RestApiUtils.makeApiCall(url, headers, fileMetaData, fileToUpload);
//        return responseJson;
//    }

    public static void main(String[] args) throws FileNotFoundException, MessagingException {

        //new EchoSignRSClient().uploadDocument();//.refreshOauthToken();
    }

}
