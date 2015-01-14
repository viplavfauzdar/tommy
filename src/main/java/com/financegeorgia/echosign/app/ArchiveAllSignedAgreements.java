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

import com.financegeorgia.echosign.util.FileUtils;
import com.financegeorgia.echosign.util.RestApiAgreements;
import com.financegeorgia.echosign.util.RestApiTokens;
import com.financegeorgia.echosign.util.RestError;

/**
 * This sample client demonstrates how to archive all signed agreements of an account. It retrieves all agreements of the current API user,
 * checks which agreements are signed, and then save them to the output location specified in FileUtils.java.
 * 
 * <p>
 * <b>IMPORTANT</b>:
 * <ol>
 * <li>Before running this sample, check that you have modified the JSON file 'Auth.json' with appropriate values. Which values need to be
 * specified is indicated in the file.</li>
 * <li>Check that the default output location in the field <code>OUTPUT_PATH</code> of FileUtils.java is suitable.</li>
 * </ol>
 * </p>
 */
public class ArchiveAllSignedAgreements {
  // File containing request body to get an access token.
  private static final String authRequestJSONFileName = "Auth.json";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      ArchiveAllSignedAgreements client = new ArchiveAllSignedAgreements();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Failure in archiving signed agreements");
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
    JSONArray agreementList = (JSONArray) myAgreements.get("userAgreementList");
    for (Object eachAgreement : agreementList) {
      JSONObject agreement = (JSONObject) eachAgreement;
      // Filter to get all signed eSign agreements of the account.
      if (agreement.get("status").equals("SIGNED") && (agreement.get("esign").equals(true))) {
        String signedAgreementId = (String) (agreement.get("agreementId"));

        // Display agreement name.
        String agreementName = (String) agreement.get("name");
        System.out.println("Agreement name: " + agreementName);

        String fileName = agreementName + "_" + System.currentTimeMillis() + ".pdf";
        System.out.println("Saving archive '" + fileName + "'");

        // Make API call to get archival stream.
        byte archiveStream[] = RestApiAgreements.getAgreementCombinedBytes(accessToken, signedAgreementId);

        if (archiveStream != null) {
          boolean success = FileUtils.saveToFile(archiveStream, FileUtils.ARCHIVED_FILES_OUTPUT_PATH, fileName);
          if (success)
            System.out.println("Successfully saved archive in '" + FileUtils.ARCHIVED_FILES_OUTPUT_PATH + "'.");
          else
            System.err.println(RestError.FILE_NOT_SAVED.errMessage);
        }
        else {
          System.err.println("Error while archiving agreement: " + agreementName);
        }
      }
    }
  }
}
