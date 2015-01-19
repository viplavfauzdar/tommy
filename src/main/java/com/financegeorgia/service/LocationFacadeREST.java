/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Audit;
import com.financegeorgia.entities.Location;
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
@Path("location")
public class LocationFacadeREST extends AbstractFacade<Location> {

    private static final Logger logger = Logger.getLogger(AbstractFacade.class);

    private static final AuditFacadeREST af = new AuditFacadeREST();

    public LocationFacadeREST() {
        super(Location.class);
    }

    @POST
    @Path("create")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
    public Location create(Location entity, @Context HttpServletRequest request) {
        entity.setUserId((Integer) request.getSession().getAttribute("userId"));
        Location location = super.create(entity);
        Audit audit = new Audit();
        audit.setTableName("location");
        audit.setTableKey(location.getId());
        if (logger.isDebugEnabled()) {
            logger.debug("Adding audit record.." + audit.toString());
        }
        af.create1(audit, request);
        return location;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})//"application/xml", 
    public void edit(@PathParam("id") Integer id, Location entity, @Context HttpServletRequest request) {
        entity.setId(id); //for an update and not an insert
        super.edit(entity);
        //find audit record based on table and key and then update it
        Audit audit = af.find("location", id).get(0);
        af.edit(audit.getId(), audit, request);
    }

    @GET
    @Path("getme")
    @Produces({"application/json"})
    public Location getMe(@Context HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        List<Location> locations = null;
        if (userId == null) {
            locations = super.findByField("Location.findByUserEmail", "email", request.getRemoteUser());
        } else {
            locations = super.findByField("Location.findByUserId", "userId", userId);
        }
        if (locations.size() > 0) {
            return locations.get(0);
        } else {
            return null;
        }
    }

    @PUT
    @Path("updateme")
    @Consumes({"application/json"})
    public void upateLoggedInUser(Location entity, @Context HttpServletRequest request) {
        Location location = getMe(request);
        logger.debug("LOGGED IN USER LOCATION: " + location);
        if (location == null) {
            create(entity, request); //insert if null
        } else {
            entity.setUserId(location.getUserId());
            edit(location.getId(), entity, request);  //else update
        }
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }
    @GET
    @Path("user/{userid}")
    @Produces({"application/xml", "application/json"})
    public Location findByUser(@PathParam("userid") Integer userid) {
        List<Location> l = super.findByField("Location.findByUserId", "userId", userid);
        if (l.size() > 0) {
            return l.get(0);
        } else {
            return null;
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Location find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Location> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Location> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

}
