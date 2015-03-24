/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Audit;
import com.financegeorgia.entities.Business;
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

/**
 *
 * @author Viplav
 */

@Path("business")
public class BusinessFacadeREST extends AbstractFacade<Business> {    
    
    private static final Logger logger = Logger.getLogger(AbstractFacade.class);
    
    private static final AuditFacadeREST af = new AuditFacadeREST(); 

    public BusinessFacadeREST() {
        super(Business.class);
    }

    @POST
    @Path("create")    
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
    public Business create(Business entity, @Context HttpServletRequest request) {
        entity.setUserId((Integer)request.getSession().getAttribute("userId"));
        Business business = super.create(entity);
        Audit audit = new Audit();
        audit.setTableName("business");
        audit.setTableKey(business.getId());        
        if(logger.isDebugEnabled()) logger.debug("Adding audit record.." + audit.toString());
        af.create1(audit, request);
        return business;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Business entity, @Context HttpServletRequest request) {
        entity.setId(id);
        super.edit(entity);
         //find audit record based on table and key and then update it
        Audit audit = af.find("business", id).get(0);
        af.edit(audit.getId(), audit, request);
    }
    
    @GET
    @Path("getme")
    @Produces({"application/json"})
    public Business getMe(@Context HttpServletRequest request) {
        Integer userId = (Integer)request.getSession().getAttribute("userId");
        List<Business> businesses = null;
        if(userId==null){
            businesses = super.findByField("Business.findByUserEmail", "email", request.getRemoteUser());            
        }else{
            businesses = super.findByField("Business.findByUserId", "userId", userId);            
        }
        if (businesses.size() > 0) {
            return businesses.get(0);
        } else {            
            return null;
        }
    }
    
    @PUT
    @Path("updateme")
    @Consumes({"application/json"})
    public void upateLoggedInUser(Business entity, @Context HttpServletRequest request) {              
        Business business = getMe(request);
        logger.debug("LOGGED IN USER LOCATION: " + business);
        if(business==null){
            create(entity, request); //insert if null
        }else{
            entity.setUserId(business.getUserId());
            edit(business.getId(), entity, request);  //else update
        }
    }   

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Business find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Business> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Business> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    
}
