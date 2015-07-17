/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import com.financegeorgia.entities.Location;
import com.financegeorgia.entities.User;
import com.financegeorgia.service.LocationFacadeREST;
import com.financegeorgia.service.UserFacadeREST;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author viplav
 */
public class SynapsePayRSClient {

    private static String clientId = "Ef2aeSO57wWssMP7NZz4", clientSecret = "oZslaR5KPu6gEI57DOw5miHklKRJNWhqdEAlBu5I";

    private static Logger logger = Logger.getLogger(SynapsePayRSClient.class);
    private static UserFacadeREST uf = new UserFacadeREST();
    private static LocationFacadeREST lf = new LocationFacadeREST();
    private static String baseURL = "https://sandbox.synapsepay.com/api/v2/";
    private static Client client = ClientBuilder.newClient();
    private static Invocation.Builder invocationBuilder = null;
    private HttpServletRequest request = null;

    private static SynapsePayRSClient syc = new SynapsePayRSClient();

    private SynapsePayRSClient() {
        //can't instantiate
    }

    public static SynapsePayRSClient getInstance() {        
        return syc;
    }
    
    public void setEnv(HttpServletRequest request){
        this.request = request;
        //PR or Others
        String myurl = request.getRequestURL().toString(); //"us.financegeorgia"; //- for mocking PR
        if(myurl.contains("tommy-financegeorgia") || myurl.contains("us.financegeorgia") || myurl.contains("pr.financegeorgia")){
            this.baseURL = "https://synapsepay.com/api/v2/";
            this.clientId = "153440c2c8c894e56338";
            this.clientSecret = "676a9cbcd7d4ced285e24cbf3599c89b58e01648";
        }
        logger.debug("Synapse Base URL: " + baseURL);
    }

    public JSONObject create() {
        try {
            User u = uf.getMe(request);
            if (logger.isDebugEnabled()) {
                logger.debug(u);
            }
            Location l = lf.getMe(request);
            if (logger.isDebugEnabled()) {
                logger.debug(l);
            }
            JSONObject obj = new JSONObject();
            obj.put("email", u.getEmail());
            obj.put("fullname", u.getFirstName() + " " + u.getLastName());
            obj.put("phonenumber", l.getPhone1());
            obj.put("ip_address", request.getRemoteAddr());
            obj.put("client_id", clientId);
            obj.put("client_secret", clientSecret);
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            String jsonText = out.toString();
            return (JSONObject) new JSONParser().parse(send(jsonText,"user/create"));
        } catch (Exception ex) {
            throw new FGException(ex);
        }
    }

    public String send(String pl, String uri) {
        logger.debug("Payload: " + pl);
        WebTarget target = client.target(baseURL + uri);
        invocationBuilder = target.request();

        Response resp = invocationBuilder.post(Entity.entity(pl, MediaType.APPLICATION_JSON));
        logger.debug(resp.getStatus());
        String ret = resp.readEntity(String.class);
        logger.debug("Response: " + ret);
        resp.close();
        return ret;
    }

    public JSONObject createAccount1(JSONObject pl) {

        WebTarget target = client.target(baseURL + "user/create");
        invocationBuilder = target.request();

        Response resp = invocationBuilder.post(Entity.entity(pl, MediaType.APPLICATION_JSON));
        logger.debug(resp.getStatus());
        JSONObject ret = (JSONObject) resp.readEntity(JSONObject.class); //throws java.util.HashMap cannot be cast to org.json.simple.JSONObject
        resp.close();
        return ret;
    }

    public JSONObject refresh(String refreshToken) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("client_id", clientId);
            obj.put("client_secret", clientSecret);
            obj.put("refresh_token", refreshToken);
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            String jsonText = out.toString();
            return (JSONObject) new JSONParser().parse(send(jsonText,"user/refresh"));
        } catch (Exception ex) {
            throw new FGException(ex);
        }

    }

    public static void main(String[] args) {

        String pl = "{\"email\": \"sdda3434@asdasd.com\",   \"fullname\": \"asdasd\",    \"phonenumber\": \"6782211212\",    \"ip_address\": \"192.168.1.101\",    \"client_id\": \"Ef2aeSO57wWssMP7NZz4\",    \"client_secret\": \"oZslaR5KPu6gEI57DOw5miHklKRJNWhqdEAlBu5I\"}";
        //SynapsePayRSClient.getInstance().createAccount(pl);

    }

}
