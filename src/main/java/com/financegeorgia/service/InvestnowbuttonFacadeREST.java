/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Investnowbutton;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author viplav
 */
@Path("investnowbutton")
public class InvestnowbuttonFacadeREST extends AbstractFacade<Investnowbutton> {
        

    public InvestnowbuttonFacadeREST() {
        super(Investnowbutton.class);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create1(Investnowbutton entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Investnowbutton entity) {
        super.edit(entity);
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Investnowbutton find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("bus/{id}")
    @Produces({"application/json"})
    public Investnowbutton findByBusId(@PathParam("id") Integer id) {
        List<Investnowbutton> invBtnList = super.findByField("Investnowbutton.findByBusId", "busId", id);
        if(invBtnList!=null && invBtnList.size()>0)
            return invBtnList.get(0);
        else
            return null;
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Investnowbutton> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Investnowbutton> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    
}
