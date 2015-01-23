/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.financegeorgia.service;

import com.financegeorgia.entities.Audit;
import com.financegeorgia.entities.Role;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

@Path("com.financegeorgia.entities.role")
class RoleFacadeREST extends AbstractFacade<Role> { //made it package local so no REST calls can be made to it
    
    private static final AuditFacadeREST af = new AuditFacadeREST();           
    

    public RoleFacadeREST() {
        super(Role.class);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create1(Role entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Role entity) {
        super.edit(entity);
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id, @Context HttpServletRequest request) {
        Audit audit = af.find("role", id).get(0);
        audit.setDeletedInd((short)1);
        af.edit(audit.getId(), audit, request);
        //super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Role find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Role> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Role> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
}
