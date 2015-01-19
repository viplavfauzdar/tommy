/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.financegeorgia.service;

import com.financegeorgia.entities.Audit;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Path("com.financegeorgia.entities.audit")
public class AuditFacadeREST extends AbstractFacade<Audit> {
    
    private Audit audit;

    private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS"); 
    public AuditFacadeREST() {
        super(Audit.class);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create1(Audit entity, @Context HttpServletRequest request) {
        //entity.setCreateTs(new Date()); //don't need it. @prepersist does it
        entity.setCreateIp(request.getRemoteAddr());
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Audit entity, @Context HttpServletRequest request) {
        entity.setId(id); //needed to set cause it is inserting
//        entity.setCreateTs(audit.getCreateTs());
//        entity.setCreateIp(audit.getCreateIp());
//        entity.setCreateUid(audit.getCreateUid());       
        entity.setUpdateTs(new Date()); //don't need it. @preupdate does it - seems inconsistent thats why doing it specifically here
        entity.setUpdateIp(request.getRemoteAddr());
        super.edit(entity);
    }

    // Never delete an audit record
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Audit find(@PathParam("id") Integer id) {
        this.audit = super.find(id);
        return audit;
    }
    
    @GET
    @Path("tableandkey/{table}/{key}")
    @Produces({"application/xml", "application/json"})
    public List<Audit> find(@PathParam("table") String table, @PathParam("key") Integer key) {
        return super.findBy2Fields("Audit.findByTableAndKey","tableName",table,"tableKey",key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Audit> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Audit> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }    
    
}
