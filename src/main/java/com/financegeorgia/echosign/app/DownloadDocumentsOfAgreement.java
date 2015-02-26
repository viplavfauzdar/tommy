/**
 * ***********************************************************************
 * ADOBE SYSTEMS INCORPORATED Copyright 2013 Adobe Systems Incorporated All
 * Rights Reserved.
 *
 * NOTICE: Adobe permits you to use, modify, and distribute this file in
 * accordance with the terms of the Adobe license agreement accompanying it. If
 * you have received this file from a source other than Adobe, then your use,
 * modification, or distribution of it requires the prior written permission of
 * Adobe.
 *************************************************************************
 */
package com.financegeorgia.echosign.app;

import com.financegeorgia.echosign.util.FileUtils;
import com.financegeorgia.echosign.util.RestApiAgreements;
import com.financegeorgia.echosign.util.RestApiTokens;
import com.financegeorgia.echosign.util.RestError;
import com.financegeorgia.utils.FGException;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This sample client demonstrates how to to download documents of the specified
 * agreement.
 *
 * <p>
 * <b>IMPORTANT</b>:
 * <ol>
 * <li>Before running this sample, check that you have modified the JSON file
 * 'Auth.json' with appropriate values. Which values need to be specified is
 * indicated in the file.</li>
 * <li>Make sure that you have specified a valid agreement ID in
 * <code>agreementId</code> below.</li>
 * <li>Check that the default output location in the field
 * <code>OUTPUT_PATH</code> of FileUtils.java is suitable.</li>
 * </ol>
 * </p>
 */
public class DownloadDocumentsOfAgreement {

    private static final Logger logger = Logger.getLogger(DownloadDocumentsOfAgreement.class);

  // TODO: Enter a valid agreement ID. Please refer to the "agreements" end-point in the API documentation to learn how to obtain IDs of
    // agreements present in EchoSign.
    private static String agreementId = "2AAABLblqZhANU3zyOATt7f-TK1BBCWAuZtx5auoJL9vNz9DsO-W_ybGh-5A6YBnP35_YxN3N6rs";

    // File containing request body to get an access token.
    private static final String authRequestJSONFileName = "Auth.json";

    private static String fileDownloadPath = null, docName = null;

    /**
     * Entry point for this sample client program.
     */
    public static void main(String args[]) {
        try {
            DownloadDocumentsOfAgreement client = new DownloadDocumentsOfAgreement();
            client.run();
        } catch (Exception e) {
            logger.fatal("Error while retrieving documents of the agreement");
            throw new FGException(e);
        }
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public void setFileDownloadPath(String fileDownloadPath) {
        this.fileDownloadPath = fileDownloadPath;
    }

    public void setDocumentName(String docName) {
        this.docName = docName;
    }

    
    /**
     * Main work function. See the class comment for details.
     */
    public void run() throws Exception {
        // Get access token to make further API calls.
        String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);

        // Display name of the agreement associated with the specified agreement ID.
        JSONObject agreementInfo = RestApiAgreements.getAgreementInfo(accessToken, agreementId);
        String agreementName = (String) agreementInfo.get("name");
        logger.debug("Agreement name: " + agreementName);

        // Fetch list of documents associated with the specified agreement.
        JSONObject docJson = RestApiAgreements.getAgreementDocuments(accessToken, agreementId);
        if (logger.isDebugEnabled()) {
            logger.debug("Doc JSON: " + docJson.toJSONString());
        }
        //JSONArray documentList = (JSONArray) docJson.get("documents");
        JSONObject document = (JSONObject) docJson.get("documents");
    //ArrayList documentList = (ArrayList) docJson.get("documents");

    //because of java.lang.ClassCastException: org.json.simple.JSONObject cannot be cast to org.json.simple.JSONArray
        //and because there is only one object in the list anyways
//    for (Object eachDocument : documentList) {
//      JSONObject document = (JSONObject) eachDocument;
        // Get ID and name of each document.
        String documentId = (String) document.get("documentId");
        String documentName = (String) document.get("name");
        if (documentName == null) {
            documentName = docName;
        }

        logger.debug("Document ID & Name: " + documentId + " - " + documentName);

      // Generate a running file name for storing locally. Note that in practice the document may not be a PDF file (e.g. the API call 
        // requested the document in its original format) and document name itself might contain an extension. For simplicity we ignore 
        // these possibilities.
        //String fileName = documentName + "_" + System.currentTimeMillis() + ".pdf";
        //need to save just one copy
        String fileName = documentName;//+ "_" + System.currentTimeMillis() + ".pdf";
        logger.debug("Saving document: " + fileName);

        // Download and save these documents.
        String filePath = null;
        if (fileDownloadPath != null) {
            filePath = fileDownloadPath;
        } else {
            filePath = FileUtils.AGREEMENT_DOCS_OUTPUT_PATH;
        }
        byte docStream[] = RestApiAgreements.getDocumentBytes(accessToken, agreementId, documentId);
        if (docStream != null) {
            boolean success = FileUtils.saveToFile(docStream, filePath, fileName);
            if (success) {
                logger.debug("Successfully saved document in '" + filePath + "'.");
            } else {
                logger.fatal(RestError.FILE_NOT_SAVED.errMessage);
            }
        } else {
            logger.fatal("Error while retrieving documents of the agreement: " + agreementName);
        }
    }
    //}

}
