/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.User;
import com.financegeorgia.utils.SaltedHash;
import com.owlike.genson.Genson;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 *
 * @author Viplav
 */
@Path("com.financegeorgia.entities.userpass")
public class UserPassREST {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("tommy_tommy_war_1.0PU");
    private static SaltedHash sh = SaltedHash.getInstance();

    //can't have query string below as it exposes the password
    //not needed as login via tomcat j_sec_check JEE web.xml auth
    @POST
    @Path("login/{username}/{password}")
    @Produces({"application/json"})
    public User login(@PathParam("username") String username, @PathParam("password") String password) {
        EntityManager em = emf.createEntityManager();
        try {
            //em.getTransaction().begin();
            //don't use SHA2 in SQL as it shows up in logs. instead use the java class.
            Query q = em.createNativeQuery("select * from user where username = ? and password1 = ?", User.class);
            q.setParameter(1, username);
            q.setParameter(2, sh.get_SHA_512_SecurePassword(password));            
            return (User)q.getSingleResult();            
            //em.getTransaction().commit();        
        } finally {
            em.close();
        }
    }
    
    @POST
    @Path("create/{username}/{password}")
    public void create(@PathParam("username") String username, @PathParam("password") String password, @Context HttpServletRequest request) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //don't use SHA2 in SQL as it shows up in logs. instead use the java class.
            Query q = em.createNativeQuery("insert into user(username, password1, create_ip) values(?,?,?)");
            q.setParameter(1, username);
            q.setParameter(2, sh.get_SHA_512_SecurePassword(password));
            q.setParameter(3, request.getRemoteAddr());
            q.executeUpdate();
            em.getTransaction().commit();        
        } finally {
            em.close();
        }
    }

    @POST
    @Path("update/{username}/{oldpassword}/{newpassword}")
    public void update(@PathParam("username") String username, @PathParam("oldpassword") String oldpassword, @PathParam("newpassword") String newpassword, @Context HttpServletRequest request) {
        EntityManager em = emf.createEntityManager();
        try {
            
            em.getTransaction().begin();
            //don't use SHA2 in SQL as it shows up in logs. instead use the java class.
            Query q = em.createNativeQuery("update user set password1 = ?, update_ip = ?, updatedate = ? where username = ? and password1 = ?");
            q.setParameter(1, sh.get_SHA_512_SecurePassword(newpassword));
            q.setParameter(2, request.getRemoteAddr());
            q.setParameter(3, new Date());            
            q.setParameter(4, username);            
            q.setParameter(5, sh.get_SHA_512_SecurePassword(oldpassword));
            q.executeUpdate();
            em.getTransaction().commit();        
        } finally {
            em.close();
        }
    }

}
