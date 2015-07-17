/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.service;

import com.financegeorgia.entities.Business;
import com.financegeorgia.entities.Investment;
import com.financegeorgia.entities.SynapseOrder;
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

/**
 *
 * @author viplav
 */
@Path("synapseorder")
public class SynapseOrderFacadeREST extends AbstractFacade<SynapseOrder> {

    private static final UserFacadeREST uf = new UserFacadeREST();
    private static final BusinessFacadeREST bf = new BusinessFacadeREST();
    private static final InvestmentFacadeREST ivf = new InvestmentFacadeREST();

    public SynapseOrderFacadeREST() {
        super(SynapseOrder.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public SynapseOrder create(SynapseOrder entity) {
        return super.create(entity);
    }

    @GET
    @Path("{orderid}/{amt}/{busid}")
    public void record(@PathParam("orderid") Integer orderid, @PathParam("amt") Integer amt, @PathParam("busid") Integer busid, @Context HttpServletRequest request) {
        //additionally track the investment
        Investment ivst = new Investment();
        ivst.setUserId((Integer) request.getSession().getAttribute("userId"));
        ivst.setBusinessId(busid);
        ivst.setAmountInvested(amt); //amount in cents for balanced
        Business bus = bf.find(busid);
        if (bus.getCashCommPercent() != null) {
            double comm = bus.getCashCommPercent();
            ivst.setCommissionTaken(new Double(comm * amt / 100).intValue());
        }
        if (bus.getEquityOffered() != null && bus.getTargetAmount() != null) {
            double eo = bus.getEquityOffered();
            double ta = bus.getTargetAmount();
            ivst.setEquityObtained(new Double(amt * eo / ta).intValue());
        }
        ivst = ivf.create(ivst);
        SynapseOrder so = new SynapseOrder();
        so.setFgIvstId(ivst.getId());
        so.setSyOrderId(orderid);
        so.setSyAmount(amt);
        so = create(so);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, SynapseOrder entity) {
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
    public SynapseOrder find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SynapseOrder> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SynapseOrder> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

}
