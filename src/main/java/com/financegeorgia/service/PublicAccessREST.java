/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Business;
import com.financegeorgia.entities.Location;
import com.financegeorgia.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.log4j.Logger;

/**
 *
 * @author Viplav
 */
@Path("public")
public class PublicAccessREST {

    private static final Logger logger = Logger.getLogger(PublicAccessREST.class);
    private static final BusinessFacadeREST bf = new BusinessFacadeREST();
    private static final LocationFacadeREST lf = new LocationFacadeREST();
    private static final InvestmentFacadeREST ivf = new InvestmentFacadeREST();
    private static final UserFacadeREST uf = new UserFacadeREST();

    /**
     * Get basic info for all businesses to display on portfolio page
     */
    @GET
    @Path("all")
    @Produces({"application/json"})
    public List<HashMap<String, Object>> findAll() {
        logger.debug("Getting all public records....!");
        List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
        List<Business> blist = bf.findAll();
        for (Business bus : blist) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("userId", bus.getUserId());
            map.put("businessId", bus.getId());
            map.put("businessName", bus.getBusinessName());
            map.put("summary", bus.getSummary());
            Location loc = lf.findByUser(bus.getUserId());
            if (loc != null) {
                map.put("address", loc.getAddress());
                map.put("city", loc.getCity());
                map.put("state", loc.getState());
                map.put("zip", loc.getZip());
            }
            map.put("targetAmount", bus.getTargetAmount());
            map.put("amountInvested", ivf.findSumByBusiness(bus.getId()));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * Get business info for specific business for display on business profile
     * page
     */
    @GET
    @Path("business/{id}")
    @Produces({"application/json"})
    public HashMap<String, Object> findBusiness(@PathParam("id") Integer id) {
        logger.debug("Getting one public records....!");
        Business bus = bf.find(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", bus.getUserId());
        map.put("businessId", bus.getId());
        map.put("businessName", bus.getBusinessName());
        map.put("summary", bus.getSummary());
        map.put("description", bus.getDescription());
        Location loc = lf.findByUser(bus.getUserId());
        if (loc != null) {
            map.put("address", loc.getAddress());
            map.put("city", loc.getCity());
            map.put("state", loc.getState());
            map.put("zip", loc.getZip());
            map.put("ws", loc.getWebsite());
            map.put("fb", loc.getFacebook());
            map.put("tw", loc.getTwitter());
            map.put("gp", loc.getGooglePlus());
            map.put("lk", loc.getLinkedin());
        }
        map.put("targetAmount", bus.getTargetAmount());
        map.put("amountInvested", ivf.findSumByBusiness(bus.getId()));
        return map;
    }

    /**
     * Get user info
     */
    @GET
    @Path("user/{id}")
    @Produces({"application/json"})
    public HashMap<String, Object> findUser(@PathParam("id") Integer id) {
        logger.debug("Getting one public records....!");
        User user = uf.find(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", user.getId());
        map.put("firstName", user.getFirstName());
        map.put("mi", user.getMi());
        map.put("lastName", user.getLastName());
        map.put("email", user.getEmail());
        map.put("aboutMe", user.getAboutMe());
        Location loc = lf.findByUser(user.getId());
        if (loc != null) {
            map.put("address", loc.getAddress());
            map.put("city", loc.getCity());
            map.put("state", loc.getState());
            map.put("zip", loc.getZip());
            map.put("ws", loc.getWebsite());
            map.put("fb", loc.getFacebook());
            map.put("tw", loc.getTwitter());
            map.put("gp", loc.getGooglePlus());
            map.put("lk", loc.getLinkedin());
        }
        return map;
    }
}
