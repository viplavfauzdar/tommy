/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.financegeorgia.service;

import java.util.Set;

/**
 *
 * @author Viplav
 */
@javax.ws.rs.ApplicationPath("rs")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.financegeorgia.service.AuditFacadeREST.class);
        resources.add(com.financegeorgia.service.BusinessFacadeREST.class);
        resources.add(com.financegeorgia.service.InvestmentFacadeREST.class);
        resources.add(com.financegeorgia.service.LocationFacadeREST.class);
        resources.add(com.financegeorgia.service.PublicAccessREST.class);
        resources.add(com.financegeorgia.service.RoleFacadeREST.class);
        resources.add(com.financegeorgia.service.UserFacadeREST.class);
        resources.add(com.financegeorgia.service.UserPassREST.class);
        resources.add(test.service_NO_BUENO.TestRolesFacadeREST.class);
        resources.add(test.service_NO_BUENO.TestUsersFacadeREST.class);
    }
    
}
