/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Audit;
import com.financegeorgia.entities.Role;
import com.financegeorgia.entities.User;
import com.financegeorgia.utils.FGException;
import com.financegeorgia.utils.FGUtils;
import com.financegeorgia.utils.SaltedHash;
import com.financegeorgia.utils.SendMail;
import java.util.List;
import javax.servlet.ServletException;
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
import org.apache.log4j.Logger;

/**
 *
 * @author Viplav
 */
//@javax.ejb.Stateless
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    //@PersistenceContext(unitName = "tommy_tommy_war_1.0PU") //this doesn't work. below needed.
    //private final EntityManager em = Persistence.createEntityManagerFactory("tommy_tommy_war_1.0PU").createEntityManager();
    private static Logger logger = Logger.getLogger(UserFacadeREST.class);
    //private final static Genson genson = new Genson();
    private static final SaltedHash sh = SaltedHash.getInstance();
    //Audit stuff
    private static final AuditFacadeREST af = new AuditFacadeREST();
    private static final RoleFacadeREST rf = new RoleFacadeREST();

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Path("create")
    @Consumes({"application/json"}) //"application/xml", 
    //@Produces({"application/json"})
    public void create1(User entity, @Context HttpServletRequest request) {
        try {
            String email = entity.getEmail();
            String pass = entity.getPassword1();
            //check recaptcha verification
            if (!(Boolean) request.getSession().getAttribute("ReCaptchaVerifed")) {
                throw new FGException("Recaptcha not verified!");
            }
            entity.setPassword1(sh.get_SHA_512_SecurePassword(entity.getPassword1()));
            logger.info("Creating.." + entity.toString());
            User user = super.create(entity);
            Role role = new Role();
            role.setEmail(user.getEmail());
            role.setRole("user"); //more secure this way. should lock call to role REST from client as someone can make themselves admin
            rf.create1(role);
            Audit audit = new Audit();
            audit.setTableName("user");
            audit.setTableKey(user.getId());
            //audit.setCreateTs(new Date());
            //audit.setCreateIp(request.getRemoteAddr());
            logger.info("Adding audit record.." + audit.toString());
            af.create1(audit, request);
            //log user in now
            request.login(email, pass); //plain password cause server.xml has SHA-512 in datasourcerealm
            request.getSession().setAttribute("userId", user.getId());
            //return user; //shouldn't return anything

            // send email but trap exception as we don't want to stop signup process if 
            // something went wrong with smtp server
            try {
                SendMail sm = new SendMail();
                sm.send(email, "", "Thank you for signing up!", "<h4>Thank you for signing up! We welcome you to Finance Georgia. You are now an important member of the Georgia investment community.</h4>\n"
                        + "<h5>- Finance Georgia Team</h5>\n"
                        + "<a href='https://pr.financegeorgia.com'><img src=\"https://pr.financegeorgia.com/Assets/Finance-Georgia-Logo.png\" alt=\"Finance Georgia\"/></a>\n"
                        + "<p><small>Finance Georgia &copy; 2015</small>");
            } catch (Exception ee) {
                logger.error(ee);
            }
        } catch (ServletException ex) {
            throw new FGException(ex);
        }
    }

    //@PUT
    @GET
    @Path("updatemypassword/{oldpass}/{newpass}")
    //@Consumes({"application/json"}) 
    public String updateMyPassword(@PathParam("oldpass") String oldpass, @PathParam("newpass") String newpass, @Context HttpServletRequest request) throws Exception {
        User user = findByEmail(request.getRemoteUser());
        String retmsg = null;
        if (user == null) {
            retmsg = "No user found by the email id of " + request.getRemoteUser();
        } else if (!user.getPassword1().equals(sh.get_SHA_512_SecurePassword(oldpass))) {
            retmsg = "The old password is incorrect!";
        } else if (user.getPassword1().equals(sh.get_SHA_512_SecurePassword(newpass))) {
            retmsg = "Cannot use the same password!";
        }
        if (retmsg == null) {
            user.setPassword1(sh.get_SHA_512_SecurePassword(newpass));
            super.edit(user);
            //find audit record based on table and key and then update it
            Audit audit = af.find("user", user.getId()).get(0);
            audit.setComments("Password successfully changed!");
            af.edit(audit.getId(), audit, request);
            retmsg = "Password successfully changed!";
        } //else {
//            Audit audit = new Audit();
//            audit.setTableName("user");
//            audit.setTableKey(user.getId()); 
//            audit.setComments("Illegal password change attempt by " + request.getRemoteUser() + " This will be reported! ");
//            af.create1(audit, request);
//            retmsg = "Illegal password change attempt by " + request.getRemoteUser() + " This will be reported! ";
//        }
        return retmsg;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})//"application/xml", 
    public void edit(@PathParam("id") Integer id, User entity, @Context HttpServletRequest request) {
        entity.setId(id); //for an update and not an insert
        //entity.setUpdateIp(request.getRemoteAddr());
        super.edit(entity);
        //find audit record based on table and key and then update it
        List<Audit> al = af.find("user", id);
        if (al.size() > 0) {
            Audit audit = al.get(0);
            af.edit(audit.getId(), audit, request);
        }
    }

    //does an update to audit instead - deleted_ind=1
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id, @Context HttpServletRequest request) {
        Audit audit = af.find("user", id).get(0);
        audit.setDeletedInd((short) 1);
        af.edit(audit.getId(), audit, request);
        //super.remove(super.find(id));
    }

    //not gonna work cause I have /rs/* blocked in web.xml
//    @GET
//    @Path("getloggedinuser")
//    @Produces({"text"})
//    public String getLoggedInUser(@Context HttpServletRequest request) {
//        return request.getRemoteUser();
//    }
    @GET
    @Path("getme")
    @Produces({"application/json"})
    public User getMe(@Context HttpServletRequest request) {
        List<User> users = super.findByField("User.findByEmail", "email", request.getRemoteUser());
        User user = null;
        if (users.size() > 0) {
            user = users.get(0);
            request.getSession().setAttribute("userId", user.getId());
            //user.setPassword1("HIDDEN"); //don't want even encrypted password returned! - doesn't work. cause password to become HIDDEN
        }
        return user;
    }

    @PUT
    @Path("updateme")
    @Consumes({"application/json"})
    //@Produces({"application/json"})
    public void upateLoggedInUser(User entity, @Context HttpServletRequest request) {
        User user = getMe(request);
        logger.debug("LOGGED IN USER: " + user);
//        if(user==null) 
//            create1(entity, request); //create if doesn't exist
//        else
        entity.setEmail(user.getEmail()); //don't want email/pass updated. That will be separate.
        entity.setPassword1(user.getPassword1());//also need these as they would otherwise be set to null
        entity.setUserType(user.getUserType());//don't want user to change type after registering
        edit(user.getId(), entity, request);
    }

    @GET
    @Path("email/{email}")
    @Produces({"application/json"})//"application/xml", 
    public User findByEmail(@PathParam("email") String email) {
        List<User> users = super.findByField("User.findByEmail", "email", email);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @GET
    @Path("verifyemail/{email}")
    @Produces({"application/json"})//"application/xml", 
    public boolean findByEmail1(@PathParam("email") String email) {
        List<User> users = super.findByField("User.findByEmail", "email", email);
        if (users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @GET
    @Path("firstname/{firstname}")
    @Produces({"application/json"})//"application/xml", 
    public List<User> findAllByFirstname(@PathParam("firstname") String firstname) {
        return super.findByAllByField("User.findAllByFirstName", "firstName", firstname);
    }

    @GET
    @Path("search/{keyword}")
    @Produces({"application/json"})//"application/xml", 
    public List<User> search(@PathParam("keyword") String keyword) {
        return super.findByAllByField("User.search", "keyword", keyword);
    }

//    @GET
//    @Path("username/{username}")
//    @Produces({"application/xml", "application/json"})
//    public List<User> findAllByUsername(@PathParam("username") String username) {
//        return super.findByField("User.findAllByUsername", "username", username);
//    }
    //can't have query string below as it exposes the password
    /*@POST
     @Path("login")
     @Consumes({"application/json"})
     @Produces({"application/json"})
     public User findByUsernameAndPassword(User entity) {
     //need to hash the text password
     entity.setP SaltedHash.getInstance().get_SHA_512_SecurePassword(entity.getPassword1());
     List<User> users = super.findBy2Fields("User.findByUsernameAndPassword", "username", username, "password1", password1);
     if (users.size() > 0) {
     return users.get(0);
     } else {
     return null;
     }
     }*/
    @GET
    @Path("{id}")
    @Produces({"application/json"})//"application/xml", 
    public User find(@PathParam("id") Integer id) {
        User user = super.find(id);
        //Just for TEST - may remove
        if (user == null) {
            throw new FGException("User not found!!");
        } else {
            return user;//super.find(id);
        }
    }

    @GET
    @Override
    @Path("all")
    @Produces({"application/json"})//"application/xml", 
    public List<User> findAll() {
        //Just for TEST - may remove
        FGUtils.getLogger().info("Getting all records!!!");
        FGUtils.info(this.getClass().getName(), "Getting all records!!!");
        Logger.getLogger(this.getClass()).info("Getting all records!!!");
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})//"application/xml", 
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }
}
