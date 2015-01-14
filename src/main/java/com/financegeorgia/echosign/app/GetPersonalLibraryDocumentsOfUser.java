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

import com.financegeorgia.echosign.util.RestApiLibraryDocuments;
import com.financegeorgia.echosign.util.RestApiTokens;

/**
 * This sample client demonstrates how to retrieve library documents of a user in the API caller's account.
 * 
 * <p>
 * <b>IMPORTANT</b>:
 * <ol>
 * <li>Before running this sample, check that you have modified the JSON file 'Auth.json' with appropriate values. Which values need to be
 * specified is indicated in the file.</li>
 * <li>Check that you have specified the email ID (in the <code>userEmail</code> field below) of a user in the API caller's account.</li>
 * </p>
 */
public class GetPersonalLibraryDocumentsOfUser {

  // File containing the request JSON for fetching access token.
  private static final String authRequestJSONFileName = "Auth.json";

  // Email ID of the user whose library documents are to be fetched
  // TODO: Enter a valid email ID.
  private static final String userEmail = "a valid email ID of a user in API caller's account";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      GetPersonalLibraryDocumentsOfUser client = new GetPersonalLibraryDocumentsOfUser();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Failure in fetching library documents of the user.");
      e.printStackTrace();
    }
  }

  /**
   * Main work function. See the class comment for details.
   */
  private void run() throws Exception {
    // Fetch access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);

    // Fetch library documents of the specified user.
    JSONObject libraryDocumentsResponse = RestApiLibraryDocuments.getLibraryDocuments(accessToken, userEmail);

    // Parse and read response.
    JSONArray libraryDocumentList = (JSONArray) libraryDocumentsResponse.get("libraryDocumentList");
    for (Object eachLibraryDocument : libraryDocumentList) {
      JSONObject libraryDocument = (JSONObject) eachLibraryDocument;
      if (libraryDocument.get("scope").equals("PERSONAL")) {
        System.out.println("Name = " + libraryDocument.get("name"));
        System.out.println("Document ID = " + libraryDocument.get("libraryDocumentId"));
        System.out.println();
      }
    }
  }
}
