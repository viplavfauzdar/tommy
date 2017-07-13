/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author Viplav
 */
@Path("TestREST")
public class TestREST {    

    /**
     * Creates a new instance of TestRESTResource
     */
    public TestREST() {
    }

    /**
     * Retrieves representation of an instance of test.TestREST
     * @return an instance of java.lang.String
     */
//    @GET
//    @Path("echo/{echo}")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public String getJson(@PathParam("echo") String echo) {
//        //TODO return proper representation object
//        return echo;
//    }
    
    @POST
    @Path("echo")
    @Consumes("application/json")
    @Produces("application/json")
    public String getJson(String echo) {
        //TODO return proper representation object
        return echo;
    }

    /**
     * PUT method for updating or creating an instance of TestREST
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
