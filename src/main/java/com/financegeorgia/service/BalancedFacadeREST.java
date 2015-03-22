/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.balancedpayments.BankAccount;
import com.balancedpayments.Customer;
import com.balancedpayments.Debit;
import com.balancedpayments.errors.HTTPError;
import com.balancedpayments.errors.NotCreated;
import com.financegeorgia.entities.Balanced;
import com.financegeorgia.entities.Business;
import com.financegeorgia.entities.Investment;
import com.financegeorgia.entities.Location;
import com.financegeorgia.entities.User;
import com.financegeorgia.utils.FGException;
import com.financegeorgia.utils.Resources;
import com.owlike.genson.Genson;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final Genson genson = new Genson();
    private static final Resources res = Resources.getInstance();
    private static final UserFacadeREST uf = new UserFacadeREST();
    private static final BusinessFacadeREST bf = new BusinessFacadeREST();
    private static final LocationFacadeREST lf = new LocationFacadeREST();
    private static final InvestmentFacadeREST ivf = new InvestmentFacadeREST();

    private User user = null;
    private Balanced balanced = null;
    private Location loc = null;

    public BalancedFacadeREST(@Context HttpServletRequest request) {
        super(Balanced.class);
        res.setResourceFile("balanced");
        com.balancedpayments.Balanced.getInstance().configure(res.getResource("apiKEY"));

        //user/getMe - to get logged in user and use in all methods below
        this.user = uf.getMe(request);
        this.loc = lf.getMe(request);
        List<Balanced> listBal = null;
        if(user!=null) listBal = super.findByField("Balanced.findByUserId", "userId", user.getId());
        for(Balanced bal: listBal){
            if(bal.getStatus().equals("DELETED")){
                bal.setBankAccountUri(null);
                this.balanced = bal;                
            }else{
                this.balanced = bal;
                break;
            }
        }
    }

    @POST
    @Path("addacc")
    @Consumes({"application/xml", "application/json"})
    public void addBankAcc(Balanced entity) {
        try {
            logger.info("Bank Account: " + entity.getBankAccountUri());
            
            Customer customer = null;
            //check is customer exists - in case account was deleted
            if(balanced==null){//.getCustomerUri()==null || balanced.getCustomerUri().equals("")){
                logger.info("adding a new customer");
                Map<String, Object> payload_customer = new HashMap<String, Object>();
                payload_customer.put("name", user.getFirstName() + " " + user.getMi() + " " + user.getLastName());
                Calendar cal = Calendar.getInstance();
                cal.setTime(user.getDob());
                payload_customer.put("dob_month", cal.get(Calendar.MONTH) + 1);
                payload_customer.put("dob_year", cal.get(Calendar.YEAR));
                //payload_customer.put("ssn_last4", "5555"); //not sure if I need it
                payload_customer.put("email", user.getEmail());

                //not sure if needed - I do. addr and dob for verfied status                
                Map<String, Object> payload_customer_address = new HashMap<String, Object>();
                payload_customer_address.put("line1", loc.getAddress());
                payload_customer_address.put("city", loc.getCity());
                payload_customer_address.put("state", loc.getState());
                payload_customer_address.put("postal_code", loc.getZip());
                payload_customer.put("address", payload_customer_address);
                customer = new Customer(payload_customer);
                customer.save();
            }else{
                logger.info("customer already exists. previous account was deleted. associating a new account");
                customer = new Customer(balanced.getCustomerUri());
            }
                

            BankAccount bankAccount = new BankAccount(entity.getBankAccountUri());

            //associate bank account to customer
            bankAccount.associateToCustomer(customer);
            bankAccount.save();

            //start ACH verification
            bankAccount.verify();

            //save both customer & bank account uri to table
            entity.setUserId(user.getId());
            entity.setCustomerUri(customer.href);
            entity.setStatus("NEW");
            super.create(entity);
        } catch (HTTPError ex) {
            throw new FGException(ex);
        }
    }

    
    @GET
    @Path("verify/{amt1}/{amt2}")
    @Produces("application/json")
    public Object verify(@PathParam("amt1") Integer amt1, @PathParam("amt2") Integer amt2) {
        try {
            BankAccount bankAccount = new BankAccount(balanced.getBankAccountUri());
            bankAccount.verification.confirm(amt1, amt2);
            balanced.setStatus(bankAccount.verification.verification_status);
            edit(balanced);
            return null;
        } catch (HTTPError ex) {
            //return "Unable to verify the account. Please check the amounts entered!";                                    
            return genson.serialize(ex);
        } catch(Exception e){
            throw new FGException(e);
        }
    }

    @GET
    @Path("debit/{amt}/{busid}")
    public void debit(@PathParam("amt") Integer amt, @PathParam("busid") Integer busid) {
        try {
            BankAccount bankAccount = new BankAccount(balanced.getBankAccountUri());
            Map debit = new HashMap();
            debit.put("amount", amt*100);
            Debit debit1 = bankAccount.debit(debit);
            debit1.appears_on_statement_as = "FG Investment";
            debit1.description = "User " + balanced.getUserId() + " invested " + amt + " in business " + busid;
            debit1.save();
            
            //additionally track the investment
            Investment ivst = new Investment();
            ivst.setUserId(balanced.getUserId());
            ivst.setBusinessId(busid);
            ivst.setAmountInvested(amt); //amount in cents for balanced
            Business bus = bf.find(busid);            
            if(bus.getCashCommPercent()!=null){
                double comm = bus.getCashCommPercent();                
                ivst.setCommissionTaken(new Double(comm*amt/100).intValue());
            }
            if(bus.getEquityOffered()!=null && bus.getTargetAmount()!=null){
                double eo = bus.getEquityOffered();
                double ta = bus.getTargetAmount();            
                ivst.setEquityObtained(new Double(amt*eo/ta).intValue());
            }
            ivf.create1(ivst);
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
            bankAccount.delete(); //or .unstore - does the same thing
            balanced.setStatus("DELETED");
            edit(balanced);
        } catch (HTTPError ex) {
            throw new FGException(ex);
        } catch (NotCreated ex) {
            throw new FGException(ex);
        }
    }

    @GET
    @Path("getmyaccount")
    @Produces({"application/xml", "application/json"})
    public Map getMyAccount() {
        try {
            if (balanced != null && balanced.getBankAccountUri()!=null) {
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
