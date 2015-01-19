/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.service_NO_BUENO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import test_NO_BUENO.TestRoles;

/**
 *
 * @author Viplav
 * 
 * tested with separate user-roles tables and generated code with roles collection. doesn't return roles as nested within users
 */

@Path("test.testroles")
public class TestRolesFacadeREST extends com.financegeorgia.service.AbstractFacade<TestRoles> {
    
    

    public TestRolesFacadeREST() {
        super(TestRoles.class);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create1(TestRoles entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, TestRoles entity) {
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
    public TestRoles find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TestRoles> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TestRoles> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    
}
