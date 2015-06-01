/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.User;
import com.financegeorgia.utils.SaltedHash;
import com.financegeorgia.utils.SendMail;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import org.apache.log4j.Logger;

/**
 * REST Web Service
 *
 * @author VXF4071
 */
@Path("resetpass")
public class PassResetREST {

    private static Logger logger = Logger.getLogger(PassResetREST.class);
    //private final static Genson genson = new Genson();
    private static final SaltedHash sh = SaltedHash.getInstance();

    /**
     * Creates a new instance of PassResetREST
     */
    public PassResetREST() {
    }

    /**
     * Retrieves representation of an instance of
     * com.financegeorgia.service.PassResetREST
     *
     * @param email
     * @param request
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{email}")
    @Produces({"text/plain"})
    public String resetPass(@PathParam("email") String email, @Context HttpServletRequest request) {
        if(logger.isDebugEnabled()) logger.debug("password reset request received from " + email);
        UserFacadeREST uf = new UserFacadeREST();
        User user = uf.findByEmail(email);
        if (user == null) {
            if(logger.isDebugEnabled()) logger.debug("Invalid email provided" + email);
            return "Invalid email provided!";            
        } else {
            user.setPassresetKey(request.getSession().getId());
            uf.edit(user);
            SendMail sm = new SendMail();
            sm.send(email, "", "Password reset request received", "<h4>Thank you for requesting a password reset. <p>Please <a href='" + request.getRequestURL() + "/" + request.getSession().getId() + "'>click here</a> to reset the password!<h4><h5>- Finance Georgia Team</h5><a href='https://pr.financegeorgia.com'><img src=\"https://pr.financegeorgia.com/Assets/Finance-Georgia-Logo.png\" alt=\"Finance Georgia\"/></a><p><small>Finance Georgia &copy; 2015</small>");
            if(logger.isDebugEnabled()) logger.debug("An email with a link to reset your password has been sent to " + email);
            return "An email with a link to reset your password has been sent! Please make sure to check your spam or junk email folder if you don't receive the email.";
        }
    }
    
    @GET
    @Path("{email}/{sesskey}")
    @Produces({"text/html"})
    public String verify(@PathParam("email") String email, @PathParam("sesskey") String sesskey, @Context HttpServletResponse response) throws IOException {
        if(logger.isDebugEnabled()) logger.debug("Verifying password reset request!");
        UserFacadeREST uf = new UserFacadeREST();
        User user = uf.findByEmail(email);
        if (user == null) {
            if(logger.isDebugEnabled()) logger.debug("Invalid email provided!");
            return "Invalid email provided!";
        } else {
            if(sesskey.equals(user.getPassresetKey())){
                String newpass = new BigInteger(50, new SecureRandom()).toString(32);
                user.setPassword1(sh.get_SHA_512_SecurePassword(newpass));
                uf.edit(user);
                SendMail sm = new SendMail();
                sm.send(email, "", "Password reset!", "<h4>Here is your new password - " + newpass +"<p>It is advisable to change your password once you log in!</h4><h5>- Finance Georgia Team</h5><a href='https://pr.financegeorgia.com'><img src=\"https://pr.financegeorgia.com/Assets/Finance-Georgia-Logo.png\" alt=\"Finance Georgia\"/></a><p><small>Finance Georgia &copy; 2015</small>");
                if(logger.isDebugEnabled()) logger.debug("Password reset and email sent to " + email);
                //important - remove the pass reset key from table so the same email link can't be used again
                user.setPassresetKey(null);
                uf.edit(user);
                //should do a response send redirect below to send a formatted HTML
                response.sendRedirect("/presetres.html");
                return "<h3>An email with a new password has been sent!</h3> <h4>It is advisable to reset your password once you log in.</h4><h4><a href='/account.html'>Click here</a> to login if you have your new password!";
            }else{
                if(logger.isDebugEnabled()) logger.debug("invalid session key!");
                return "invalid session key!";
            }                        
        }
    }

    
}
