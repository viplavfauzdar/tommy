/*************************************************************************
 * ADOBE SYSTEMS INCORPORATED
 * Copyright 2013 Adobe Systems Incorporated
 * All Rights Reserved.
 * 
 * NOTICE: Adobe permits you to use, modify, and distribute this file in accordance with the
 * terms of the Adobe license agreement accompanying it. If you have received this file from a
 * source other than Adobe, then your use, modification, or distribution of it requires the prior
 * written permission of Adobe.
 **************************************************************************/

package com.financegeorgia.echosign.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.financegeorgia.echosign.util.RestApiAgreements;
import com.financegeorgia.echosign.util.RestApiLibraryDocuments;
import com.financegeorgia.echosign.util.RestApiTokens;

/**
 * This sample client demonstrates how to send an agreement using a library document ID.
 * 
 * <p>
 * <b>IMPORTANT</b>: Before running this sample, check that you have modified the JSON files 'Auth.json' and 'SendAgreementInteractive.json'
 * with appropriate values. Which values need to be specified is indicated in the files.
 * </p>
 */
public class SendAgreementUsingLibraryDocument {
  // File containing the request JSON for fetching access token.
  private static final String authRequestJSONFileName = "Auth.json";

  // File containing the request JSON for sending an agreement.
  private static final String sendAgreementJSONFileName = "SendAgreementInteractive.json";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      SendAgreementUsingLibraryDocument client = new SendAgreementUsingLibraryDocument();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Failure in sending the agreemnet using the library document ID specified.");
      e.printStackTrace();
    }
  }

  /**
   * Entry point for this sample client program.
   */
  private void run() throws Exception {
    // Fetch access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);

    // Fetch library documents of the user using access token from above.
    JSONObject libraryDocumentsResponse = RestApiLibraryDocuments.getLibraryDocuments(accessToken);

    // Retrieve library documents list for the user and fetch the document ID of first library document.
    JSONArray libraryDocumentList = (JSONArray) libraryDocumentsResponse.get("libraryDocumentList");
    
    String libraryDocumentId = null;
    
    // Fetch the first personal or shared library document of the user.
    for (Object eachLibraryDocument : libraryDocumentList) {
      JSONObject libraryDocument = (JSONObject) eachLibraryDocument;
      if (libraryDocument.get("scope").equals("SHARED") || libraryDocument.get("scope").equals("PERSONAL")) {
        libraryDocumentId = (String) libraryDocument.get("libraryDocumentId");
        break;
      }
    }
    
    if (libraryDocumentId != null && !libraryDocumentId.isEmpty()) {
      // Send agreement using this library document ID retrieved from above.
      JSONObject sendAgreementResponse = RestApiAgreements.sendAgreement(accessToken, sendAgreementJSONFileName, libraryDocumentId,
                                                                         RestApiAgreements.DocumentIdentifierName.LIBRARY_DOCUMENT_ID);

      // Parse and read response.
      System.out.println("Agreement Sent. Agreement ID = " + sendAgreementResponse.get("agreementId"));
    }
    else {
      System.err.println("No documents found.");
    }
  }
}
