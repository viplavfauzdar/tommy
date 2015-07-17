/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Location;
import com.financegeorgia.entities.SynapseUser;
import com.financegeorgia.entities.User;
import com.financegeorgia.utils.SynapsePayRSClient;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author viplav
 */
@Path("synapseuser")
public class SynapseUserFacadeREST extends AbstractFacade<SynapseUser> {

    private static Logger logger = Logger.getLogger(SynapseUserFacadeREST.class);
    private static SynapsePayRSClient syn = SynapsePayRSClient.getInstance();
    
    public SynapseUserFacadeREST() {
        super(SynapseUser.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public SynapseUser create(SynapseUser entity) {
        return super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, SynapseUser entity) {
        super.edit(entity);
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }
    @GET
    @Path("getme")
    @Produces({"application/json"})
    public SynapseUser getMe(@Context HttpServletRequest request) {
        SynapseUser su = null;
        List<SynapseUser> suList = super.findByField("SynapseUser.findByFgUserId", "fgUserId", request.getSession().getAttribute("userId"));
        if (suList != null && suList.size() > 0) {
            su = suList.get(0);
        }
        //if user doesn't exist
        if (su == null) {
            //do nothing. return null. client will call createMe
        } else {
            //obtain oauth using refresh-token
            syn.setEnv(request);
            JSONObject obj = syn.refresh(su.getSyRefreshToken());
            logger.debug("SynapseUser Object: " + obj.toJSONString());
            String oauth = (String) obj.get("oauth_consumer_key");
            if(oauth!=null) su.setSyOauthToken(oauth);            
            edit(su);
        }
        return su;
    }

    @GET
    @Path("createme")
    @Produces({"application/json"})
    public SynapseUser createMe(@Context HttpServletRequest request) {
        //create user in synapse first and then in tommy  
        syn.setEnv(request);
        JSONObject obj = syn.create();
        //create tommy record
        SynapseUser su = new SynapseUser();
        if ((Boolean) obj.get("success")) {
            su.setFgUserId(((Integer) request.getSession().getAttribute("userId")));
            su.setSyUserId(((Long) obj.get("user_id")).intValue());
            su.setSyUsername((String) obj.get("username"));
            su.setSyRefreshToken((String) obj.get("refresh_token"));
            su.setSyOauthToken((String) obj.get("oauth_consumer_key"));
            su = create(su);
        }
        return su;
    }
    
    @GET
    @Path("fguserid/{id}")
    @Produces({"application/json"})
    public SynapseUser findByFgUserId(@PathParam("id") Integer id) {
        List<SynapseUser> suLst = super.findByField("SynapseUser.findByFgUserId", "fgUserId", id);
        if(suLst!=null && suLst.size()>0)
            return suLst.get(0);
        else
            return null;   
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public SynapseUser find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SynapseUser> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SynapseUser> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

}
