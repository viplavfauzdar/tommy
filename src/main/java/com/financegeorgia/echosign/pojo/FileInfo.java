
package com.financegeorgia.echosign.pojo;

import com.google.gson.annotations.Expose;

public class FileInfo {

    @Expose
    private String transientDocumentId;

    /**
     * 
     * @return
     *     The transientDocumentId
     */
    public String getTransientDocumentId() {
        return transientDocumentId;
    }

    /**
     * 
     * @param transientDocumentId
     *     The transientDocumentId
     */
    public void setTransientDocumentId(String transientDocumentId) {
        this.transientDocumentId = transientDocumentId;
    }

}
