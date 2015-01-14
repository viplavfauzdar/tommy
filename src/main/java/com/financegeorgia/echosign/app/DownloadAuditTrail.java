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

import org.json.simple.JSONObject;

import com.financegeorgia.echosign.util.FileUtils;
import com.financegeorgia.echosign.util.RestApiAgreements;
import com.financegeorgia.echosign.util.RestApiTokens;
import com.financegeorgia.echosign.util.RestError;

/**
 * This sample client demonstrates how to to download audit trail of the specified agreement.
 * 
 * <p>
 * <b>IMPORTANT</b>:
 * <ol>
 * <li>Before running this sample, check that you have modified the JSON file 'Auth.json' with appropriate values. Which values need to be
 * specified is indicated in the file.</li>
 * <li>Make sure that you have specified a valid agreement ID in <code>agreementId</code> below.</li>
 * <li>Check that the default output location in the field <code>OUTPUT_PATH</code> of FileUtils.java is suitable.</li>
 * </ol>
 * </p>
 */
public class DownloadAuditTrail {
  // TODO: Enter a valid agreement ID. Please refer to the "agreements" end-point in the API documentation to learn how to obtain IDs of
  // agreements present in EchoSign.
  private static String agreementId = "a valid agreement ID";

  // File containing request body to get an access token.
  private static final String authRequestJSONFileName = "Auth.json";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      DownloadAuditTrail client = new DownloadAuditTrail();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Error while retrieving audit trail for agreement");
      e.printStackTrace();
    }
  }

  /**
   * Main work function. See the class comment for details.
   */
  private void run() throws Exception {
    // Get access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);

    // Display name of the agreement associated with the specified agreement ID.
    JSONObject agreementInfo = RestApiAgreements.getAgreementInfo(accessToken, agreementId);
    String agreementName = (String) agreementInfo.get("name");
    System.out.println("Agreement name: " + agreementName);

    String fileName = agreementName + "_" + System.currentTimeMillis() + ".pdf";
    System.out.println("Saving audit report '" + fileName + "'");

    // Make API call to get audit trail of this agreement.
    byte auditStream[] = RestApiAgreements.getAgreementAuditTrailBytes(accessToken, agreementId);

    if (auditStream != null) {
      boolean success = FileUtils.saveToFile(auditStream, FileUtils.AUDIT_FILES_OUTPUT_PATH, fileName);
      if (success)
        System.out.println("Successfully saved audit report in '" + FileUtils.AUDIT_FILES_OUTPUT_PATH + "'.");
      else
        System.err.println(RestError.FILE_NOT_SAVED.errMessage);
    }
    else {
      System.err.println("Error while retrieving audit trail for agreement: " + agreementName);
    }
  }
}
