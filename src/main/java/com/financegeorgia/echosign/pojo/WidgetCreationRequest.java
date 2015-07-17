
package com.financegeorgia.echosign.pojo;

import com.google.gson.annotations.Expose;

public class WidgetCreationRequest {

    @Expose
    private WidgetCreationInfo widgetCreationInfo;
    @Expose
    private String AppliesTo;

    /**
     * 
     * @return
     *     The widgetCreationInfo
     */
    public WidgetCreationInfo getWidgetCreationInfo() {
        return widgetCreationInfo;
    }

    /**
     * 
     * @param widgetCreationInfo
     *     The widgetCreationInfo
     */
    public void setWidgetCreationInfo(WidgetCreationInfo widgetCreationInfo) {
        this.widgetCreationInfo = widgetCreationInfo;
    }

    /**
     * 
     * @return
     *     The AppliesTo
     */
    public String getAppliesTo() {
        return AppliesTo;
    }

    /**
     * 
     * @param AppliesTo
     *     The AppliesTo
     */
    public void setAppliesTo(String AppliesTo) {
        this.AppliesTo = AppliesTo;
    }

}
