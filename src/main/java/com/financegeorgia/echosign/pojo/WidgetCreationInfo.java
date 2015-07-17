
package com.financegeorgia.echosign.pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class WidgetCreationInfo {

    @Expose
    private String name;
    @Expose
    private SecurityOptions securityOptions;
    @Expose
    private WidgetCompletionInfo widgetCompletionInfo;
    @Expose
    private List<FileInfo> fileInfos = new ArrayList<FileInfo>();
    @Expose
    private String signatureFlow;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The securityOptions
     */
    public SecurityOptions getSecurityOptions() {
        return securityOptions;
    }

    /**
     * 
     * @param securityOptions
     *     The securityOptions
     */
    public void setSecurityOptions(SecurityOptions securityOptions) {
        this.securityOptions = securityOptions;
    }

    /**
     * 
     * @return
     *     The widgetCompletionInfo
     */
    public WidgetCompletionInfo getWidgetCompletionInfo() {
        return widgetCompletionInfo;
    }

    /**
     * 
     * @param widgetCompletionInfo
     *     The widgetCompletionInfo
     */
    public void setWidgetCompletionInfo(WidgetCompletionInfo widgetCompletionInfo) {
        this.widgetCompletionInfo = widgetCompletionInfo;
    }

    /**
     * 
     * @return
     *     The fileInfos
     */
    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }

    /**
     * 
     * @param fileInfos
     *     The fileInfos
     */
    public void setFileInfos(List<FileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    /**
     * 
     * @return
     *     The signatureFlow
     */
    public String getSignatureFlow() {
        return signatureFlow;
    }

    /**
     * 
     * @param signatureFlow
     *     The signatureFlow
     */
    public void setSignatureFlow(String signatureFlow) {
        this.signatureFlow = signatureFlow;
    }

}
