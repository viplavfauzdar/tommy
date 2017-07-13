/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Business;
import com.financegeorgia.utils.FGException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import org.apache.log4j.Logger;

/**
 *
 * @author viplav
 */
@Path("bp")
public class BusinessProfileREST {
    
    private static final Logger logger = Logger.getLogger(BusinessProfileREST.class);
    private static final BusinessFacadeREST bf = new BusinessFacadeREST();
    
    @GET
    @Path("/{name}")
    public void findBusinessProfileByName(@PathParam("name") String name, @Context HttpServletResponse response){
        logger.debug("Looking up business: " + name);
        Business bus = bf.findNyName(name);
        try {
            if(bus!=null){
                response.sendRedirect("/businessprofile.html#/" + bus.getId() + "#" + bus.getUserId());
                //request.getRequestDispatcher("/businessprofile.html#/" + bus.getId() + "#" + bus.getUserId()).forward(request, response); //doesn't work
            }else{
                response.sendError(404);                
            }
        } catch (Exception ex) {
            throw new FGException(ex);
        }
        //return null;
    }
}
