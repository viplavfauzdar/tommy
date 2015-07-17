
package com.financegeorgia.echosign.pojo;

import com.google.gson.annotations.Expose;

public class SecurityOptions {

    @Expose
    private String passwordProtection;
    @Expose
    private String kbaProtection;
    @Expose
    private String webIdentityProtection;
    @Expose
    private String protectOpen;
    @Expose
    private String internalPassword;
    @Expose
    private String externalPassword;
    @Expose
    private String openPassword;

    /**
     * 
     * @return
     *     The passwordProtection
     */
    public String getPasswordProtection() {
        return passwordProtection;
    }

    /**
     * 
     * @param passwordProtection
     *     The passwordProtection
     */
    public void setPasswordProtection(String passwordProtection) {
        this.passwordProtection = passwordProtection;
    }

    /**
     * 
     * @return
     *     The kbaProtection
     */
    public String getKbaProtection() {
        return kbaProtection;
    }

    /**
     * 
     * @param kbaProtection
     *     The kbaProtection
     */
    public void setKbaProtection(String kbaProtection) {
        this.kbaProtection = kbaProtection;
    }

    /**
     * 
     * @return
     *     The webIdentityProtection
     */
    public String getWebIdentityProtection() {
        return webIdentityProtection;
    }

    /**
     * 
     * @param webIdentityProtection
     *     The webIdentityProtection
     */
    public void setWebIdentityProtection(String webIdentityProtection) {
        this.webIdentityProtection = webIdentityProtection;
    }

    /**
     * 
     * @return
     *     The protectOpen
     */
    public String getProtectOpen() {
        return protectOpen;
    }

    /**
     * 
     * @param protectOpen
     *     The protectOpen
     */
    public void setProtectOpen(String protectOpen) {
        this.protectOpen = protectOpen;
    }

    /**
     * 
     * @return
     *     The internalPassword
     */
    public String getInternalPassword() {
        return internalPassword;
    }

    /**
     * 
     * @param internalPassword
     *     The internalPassword
     */
    public void setInternalPassword(String internalPassword) {
        this.internalPassword = internalPassword;
    }

    /**
     * 
     * @return
     *     The externalPassword
     */
    public String getExternalPassword() {
        return externalPassword;
    }

    /**
     * 
     * @param externalPassword
     *     The externalPassword
     */
    public void setExternalPassword(String externalPassword) {
        this.externalPassword = externalPassword;
    }

    /**
     * 
     * @return
     *     The openPassword
     */
    public String getOpenPassword() {
        return openPassword;
    }

    /**
     * 
     * @param openPassword
     *     The openPassword
     */
    public void setOpenPassword(String openPassword) {
        this.openPassword = openPassword;
    }

}
