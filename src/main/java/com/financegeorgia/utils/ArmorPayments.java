/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import com.financegeorgia.entities.ArmorAccount;
import com.financegeorgia.entities.ArmorAuthURL;
import com.financegeorgia.entities.ArmorOrder;
import com.owlike.genson.Genson;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ArmorPayments {
    
    private static String apiKey = "73066f8041cc3d5a9ad7e2a3b9823416", apiSecret = "ca9fd3568f4fb0055255d0cce1b24c515250426b75aadb2bd893be5d1dc57d7f", 
            nowAsISO = null, signature = null;

    private static Logger logger = Logger.getLogger(ArmorPayments.class);
    private static String baseURL = "https://sandbox.armorpayments.com/";
    private static Client client = ClientBuilder.newClient();
    private static Invocation.Builder invocationBuilder = null;

    private static ArmorPayments ap = new ArmorPayments();
    
    private ArmorPayments(){
        //can't instantiate
    }
    
    public static ArmorPayments getInstance(){
        return ap;
    }
    
    public void setAuthHeaders(String method, String uri) {
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        this.nowAsISO = df.format(new Date());
        String string_to_sign = apiSecret + ":" + method + ":/" + uri + ":" + nowAsISO;
        SaltedHash sh = SaltedHash.getInstance();
        this.signature = sh.get_SHA_512_SecurePassword(string_to_sign);
        String headers = "\nX_ARMORPAYMENTS_APIKEY:" + apiKey
                + "\nX_ARMORPAYMENTS_REQUESTTIMESTAMP:" + nowAsISO
                + "\nX_ARMORPAYMENTS_SIGNATURE:" + signature;  
        logger.debug(headers);
        WebTarget target = client.target(baseURL + uri);
        invocationBuilder = target.request();
        invocationBuilder.header("X_ARMORPAYMENTS_APIKEY", apiKey);
        invocationBuilder.header("X_ARMORPAYMENTS_REQUESTTIMESTAMP", nowAsISO);
        invocationBuilder.header("X_ARMORPAYMENTS_SIGNATURE", signature);
    }
    

    public void getAllAccounts() {      
        setAuthHeaders("GET","accounts");
        Response resp = invocationBuilder.get();
        logger.debug(resp.getStatus());
        logger.debug(resp.readEntity(String.class));
        resp.close();
    }
    
    public void getOneAccount(String acc) {      
        setAuthHeaders("GET","accounts/" + acc);
        Response resp = invocationBuilder.get();
        logger.debug(resp.getStatus());
        logger.debug(resp.readEntity(String.class));
        resp.close();
    }
        
    public void createAccount(ArmorAccount aa) {                                    
        setAuthHeaders("POST","accounts");
        Response resp = invocationBuilder.post(Entity.entity(aa, MediaType.APPLICATION_JSON));
        logger.debug(resp.getStatus());
        logger.debug(resp.readEntity(String.class));
        resp.close();
    }
    
    //Really only one user in account
    public void getUserInAccount(String acc){
        setAuthHeaders("GET","accounts/" + acc + "/users");
        Response resp = invocationBuilder.get();
        logger.debug(resp.getStatus());
        logger.debug(resp.readEntity(String.class));
        resp.close();
    }
    
    //acc - seller account
    public void createOrder(String acc){        
        ArmorOrder ao = new ArmorOrder();
        ao.setBuyer_id("150528197060");
        ao.setSeller_id("150528197586");
        ao.setAmount("500");
        ao.setType("6");
        ao.setSummary("Another test order");
        setAuthHeaders("POST","accounts/" + acc + "/orders");
        Response resp = invocationBuilder.post(Entity.entity(ao, MediaType.APPLICATION_JSON));
        logger.debug(resp.getStatus());
        logger.debug(resp.readEntity(String.class));
        resp.close();
    }
    
    //acc - seller account
    public void getOrders(String acc){
        setAuthHeaders("GET","accounts/" + acc + "/orders");
        Response resp = invocationBuilder.get();
        logger.debug(resp.getStatus());
        logger.debug(resp.readEntity(String.class));
        resp.close();
    }
    
    public void getOneOrder(){
        //by order number - doesnt seem to work. no rest call for it.
    }
    
    //acc - seller account, userId - seller user id, orderId - sellers order for which payment is being requested
    public void getPaymentWidget(String acc, String userId, String orderId){
        ArmorAuthURL au = new ArmorAuthURL();
        au.setUri("/accounts/" + acc + "/orders/" + orderId + "/paymentinstructions"); // - /accounts/<seller acc>/orders/<order id>/paymentinstructions
        au.setAction("view");
        setAuthHeaders("POST","accounts/" + acc + "/users/" + userId + "/authentications");
        Response resp = invocationBuilder.post(Entity.entity(au, MediaType.APPLICATION_JSON));
        logger.debug(resp.getStatus());
        logger.debug(resp.readEntity(String.class));
        resp.close();        
    }

}
