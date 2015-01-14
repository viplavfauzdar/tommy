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

import com.financegeorgia.echosign.util.RestApiTokens;
import com.financegeorgia.echosign.util.RestApiUsers;

/**
 * This sample client demonstrates how to get information of the users in an account.
 * 
 * <p>
 * <b>IMPORTANT</b>: Before running this sample, check that you have modified the JSON file 'Auth.json' with appropriate values. Which
 * values need to be specified is indicated in the file.
 * </p>
 */
public class GetUsersInAccount {
  // File containing request body to get an access token.
  private static final String authRequestJSONFileName = "Auth.json";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      GetUsersInAccount client = new GetUsersInAccount();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Failure in getting details of the user in the account.");
      e.printStackTrace();
    }
  }

  /**
   * Main work function. See the class comment for details.
   */
  public void run() throws Exception {
    // Get access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);

    // Make API call to get information about users in the account.
    JSONObject userResponse = RestApiUsers.getAllUsersOfMyAccount(accessToken);

    // Display user ID and email of each user.
    JSONArray usersList = (JSONArray) userResponse.get("userInfoList");
    for (Object eachUser : usersList) {
      JSONObject user = (JSONObject) eachUser;
      System.out.println("UserEmail: " + user.get("email"));
      System.out.println("UserID: " + user.get("userId"));
      System.out.println();
    }
  }
}