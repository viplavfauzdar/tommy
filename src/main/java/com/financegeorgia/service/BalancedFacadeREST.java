/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.balancedpayments.BankAccount;
import com.balancedpayments.Customer;
import com.balancedpayments.errors.HTTPError;
import com.balancedpayments.errors.NotCreated;
import com.financegeorgia.entities.Balanced;
import com.financegeorgia.entities.User;
import com.financegeorgia.utils.FGException;
import com.financegeorgia.utils.Resources;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
@Path("balanced")
public class BalancedFacadeREST extends AbstractFacade<Balanced> {

    private static final Logger logger = Logger.getLogger(BalancedFacadeREST.class);
    private static final Resources res = Resources.getInstance();
    private static final UserFacadeREST uf = new UserFacadeREST();

    private User user = null;
    private Balanced balanced = null;

    public BalancedFacadeREST(@Context HttpServletRequest request) {
        super(Balanced.class);
        res.setResourceFile("balanced");
        com.balancedpayments.Balanced.getInstance().configure(res.getResource("apiKEY"));

        //user/getMe - to get logged in user and use in all methods below
        this.user = uf.getMe(request);
        List<Balanced> listBal = super.findByField("Balanced.findByUserId", "userId", user.getId());
        if (listBal.size() > 0) {
            this.balanced = listBal.get(0);
        }
    }

    @POST
    @Path("addacc")
    @Consumes({"application/xml", "application/json"})
    public void addBankAcc(Balanced entity) {
        try {
            logger.info("Bank Account: " + entity.getBankAccountUri());

            Map<String, Object> payload_customer = new HashMap<String, Object>();
//            payload_customer.put("name", "Jane Doe");
//            payload_customer.put("dob_month", 7);
//            payload_customer.put("dob_year", 1978);
//            payload_customer.put("ssn_last4", "5555");

            payload_customer.put("name", user.getFirstName() + " " + user.getMi() + " " + user.getLastName());
            Calendar cal = Calendar.getInstance();
            cal.setTime(user.getDob());
            payload_customer.put("dob_month", cal.get(Calendar.MONTH) + 1);
            payload_customer.put("dob_year", cal.get(Calendar.YEAR));
            //payload_customer.put("ssn_last4", "5555"); //not sure if I need it
            payload_customer.put("email", user.getEmail());

            //not sure if needed - I do. addr and dob for verfied status
//            Map<String, Object> payload_customer_address = new HashMap<String, Object>();
//            payload_customer_address.put("city", "Decatur");
//            payload_customer_address.put("state", "GA");
//            payload_customer_address.put("postal_code", "12345");
//            payload_customer.put("address", payload_customer_address);
            Customer customer = new Customer(payload_customer);
            customer.save();

            BankAccount bankAccount = new BankAccount(entity.getBankAccountUri());

            //associate bank account to customer
            bankAccount.associateToCustomer(customer);

            //start ACH verification
            bankAccount.verify();

            //save both customer & bank account uri to table
            entity.setUserId(user.getId());
            entity.setCustomerUri(customer.href);
            super.create(entity);
        } catch (HTTPError ex) {
            throw new FGException(ex);
        }
    }

    @GET
    @Path("verify/{amt1}/{amt2}")
    public void verify(@PathParam("amt1") Integer amt1, @PathParam("amt2") Integer amt2) {
        try {
            BankAccount bankAccount = new BankAccount(balanced.getBankAccountUri());
            bankAccount.verification.confirm(amt1, amt2);
        } catch (HTTPError ex) {
            throw new FGException(ex);
        }

    }

    @GET
    @Path("debit/{amt}")
    public void debit(@PathParam("amt") Integer amt) {
        try {
            BankAccount bankAccount = new BankAccount(balanced.getBankAccountUri());
            Map debit = new HashMap();
            debit.put("amount", amt);
            bankAccount.debit(debit);
        } catch (HTTPError ex) {
            throw new FGException(ex);
        }
    }

    @GET
    @Path("credit/{amt}")
    public void credit(@PathParam("amt") Integer amt) {
        try {
            BankAccount bankAccount = new BankAccount(balanced.getBankAccountUri());
            Map credit = new HashMap();
            credit.put("amount", amt);
            bankAccount.credit(credit);
        } catch (HTTPError ex) {
            throw new FGException(ex);
        }
    }

    @DELETE
    public void delete() {
        try {
            BankAccount bankAccount = new BankAccount(balanced.getBankAccountUri());
            bankAccount.delete();
        } catch (HTTPError ex) {
            throw new FGException(ex);
        } catch (NotCreated ex) {
            throw new FGException(ex);
        }
    }

    @GET
    @Path("getme")
    @Produces({"application/xml", "application/json"})
    public Map getMe() {
        try {
            if (balanced != null) {
                BankAccount bankAccount = new BankAccount(balanced.getBankAccountUri());
                Map<String, String> map = new HashMap<String, String>();
                map.put("nameOnAccount", bankAccount.name);
                map.put("accountNumber", bankAccount.account_number);
                map.put("routingNumber", bankAccount.routing_number);
                map.put("accountType", bankAccount.account_type);
                map.put("verificationStatus", bankAccount.verification.verification_status);
                return map;
            } else {
                return null;
            }
        } catch (HTTPError ex) {
            throw new FGException(ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Balanced entity) {
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
    public Balanced find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Balanced> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Balanced> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

}
