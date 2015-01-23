/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Investment;
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

/**
 *
 * @author Viplav
 */

@Path("investment")
public class InvestmentFacadeREST extends AbstractFacade<Investment> {



    public InvestmentFacadeREST() {
        super(Investment.class);
    }

    @POST
    @Path("create")
    @Consumes({"application/xml", "application/json"})
    public void create1(Investment entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Investment entity) {
        super.edit(entity);
    }

    
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }

    @GET
    @Path("sumbyuser/{userid}")
    @Produces({"application/json"})
    public Object findSumByUser(@PathParam("userid") Integer userid) {
        return getEntityManager().createNativeQuery("SELECT SUM(amount_invested) FROM investment WHERE (user_id = ?) GROUP BY user_id").setParameter(1, userid).getSingleResult();        
    }
        
    @GET
    @Path("sumbybusiness/{businessid}")
    @Produces({"application/json"})
    public Object findSumByBusiness(@PathParam("businessid") Integer businessid) {        
        return getEntityManager().createNativeQuery("SELECT SUM(amount_invested) FROM investment WHERE (business_id = ?)").setParameter(1, businessid).getSingleResult();        
    }        
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Investment find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Investment> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Investment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    //investments made by me in various businesses
    @GET
    @Path("getbyme")
    @Produces({"application/json"})
    public List<Investment> getByMe(@Context HttpServletRequest request) {
        Integer userId = (Integer)request.getSession().getAttribute("userId");
        List<Investment> investments = null;
        if(userId==null){
            investments = super.findByField("Investment.findByUserEmail", "email", request.getRemoteUser());            
        }else{
            investments = super.findByField("Investment.findByUserId", "userId", userId);            
        }
        return investments;
    }

    //investments made in my business
    @GET
    @Path("getinme")
    @Produces({"application/json"})
    public List<Investment> getInMe(@Context HttpServletRequest request) {
        Integer userId = (Integer)request.getSession().getAttribute("userId");
        List<Investment> investments = null;
        if(userId==null){
            investments = super.findByField("Investment.findByBusinessAndUserEmail", "email", request.getRemoteUser());            
        }else{
            investments = super.findByField("Investment.findByBusinessAndUserId", "userId", userId);            
        }
        return investments;
    }
}
