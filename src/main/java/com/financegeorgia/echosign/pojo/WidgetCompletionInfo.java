
package com.financegeorgia.echosign.pojo;

import com.google.gson.annotations.Expose;

public class WidgetCompletionInfo {

    @Expose
    private String deframe;
    @Expose
    private String url;

    /**
     * 
     * @return
     *     The deframe
     */
    public String getDeframe() {
        return deframe;
    }

    /**
     * 
     * @param deframe
     *     The deframe
     */
    public void setDeframe(String deframe) {
        this.deframe = deframe;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
