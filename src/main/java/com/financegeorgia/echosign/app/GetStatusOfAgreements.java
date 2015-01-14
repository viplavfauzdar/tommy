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
import com.financegeorgia.echosign.util.RestApiTokens;

/**
 * This sample client demonstrates how to to get status of all agreements of an account and display their agreement ID, name and status.
 * 
 * <p>
 * <b>IMPORTANT</b>: Before running this sample, check that you have modified the JSON file 'Auth.json' with appropriate values. Which
 * values need to be specified is indicated in the file.
 * </p>
 */
public class GetStatusOfAgreements {
  // File containing request body to get an access token.
  private static final String authRequestJSONFileName = "Auth.json";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      GetStatusOfAgreements client = new GetStatusOfAgreements();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Failure in getting status of the agreements.");
      e.printStackTrace();
    }
  }

  /**
   * Main work function. See the class comment for details.
   */
  private void run() throws Exception {
    // Get access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);

    // Get list of all agreements of the account.
    JSONObject myAgreements = RestApiAgreements.getMyAgreements(accessToken);

    if (myAgreements != null) {
      // Display agreement ID, name and status of each agreement.
      JSONArray agreementList = (JSONArray) myAgreements.get("userAgreementList");
      for (Object eachAgreement : agreementList) {
        JSONObject agreement = (JSONObject) eachAgreement;
        System.out.println("AgreementName: " + agreement.get("name"));
        System.out.println("AgreementID: " + agreement.get("agreementId"));
        System.out.println("AgreementStatus: " + agreement.get("status"));
        System.out.println();
      }
    }
  }
}