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

import com.financegeorgia.echosign.util.RestApiAgreements;
import com.financegeorgia.echosign.util.RestApiAgreements.DocumentIdentifierName;
import com.financegeorgia.echosign.util.RestApiTokens;
import com.financegeorgia.echosign.util.RestApiUtils;

/**
 * This sample client demonstrates how to send an agreement using a transient document ID. See
 * {@link RestApiAgreements#postTransientDocument} for a definition of transient documents.
 * 
 * <p>
 * <b>IMPORTANT</b>:
 * <ol>
 * <li>Before running this sample, check that you have modified the JSON files 'Auth.json' and 'SendAgreement.json' with appropriate values.
 * Which values need to be specified is indicated in the files.</li>
 * <li>Check that the default input file in the field <code>fileToBeUploaded</code> below is suitable.</li>
 * <li>Check that the name to be given to the uploaded file in the field <code>uploadedFileName</code> below is suitable. This name can be
 * different from the original file name.</li>
 * </ol>
 * </p>
 */
public class SendAgreementUsingTransientDocument {

  // File containing the request JSON for fetching access token.
  private static final String authRequestJSONFileName = "Auth.json";

  // File containing the request JSON for sending an agreement.
  private static final String sendAgreementJSONFileName = "SendAgreement.json";

  // Name of the file to be uploaded for sending an agreement.
  // TODO: Specify file name of choice here. The file must exist in the "requests" sub-package.
  private static final String fileToBeUploaded = "Sample.pdf";

  // Mime-type of the file being uploaded.
  // TODO: Change this depending on actual file used.
  private static final String mimeType = RestApiUtils.MimeType.PDF.toString();

  // Name to be given to the file after uploading it.
  // TODO: Specify a file name of choice, ensuring that its name consists only of characters in the ASCII character set (given this basic
  // sample implementation).
  private static final String uploadedFileName = "UploadedSample.pdf";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      SendAgreementUsingTransientDocument client = new SendAgreementUsingTransientDocument();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Failure in sending the agreement using the transient document ID.");
      e.printStackTrace();
    }
  }

  /**
   * Main work function. See the class comment for details.
   */
  private void run() throws Exception {
    // Fetch access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);

    // Upload a transient document and retrieve transient document ID from the response.
    JSONObject uploadDocumentResponse = RestApiAgreements.postTransientDocument(accessToken, mimeType, fileToBeUploaded, uploadedFileName);
    String transientDocumentId = (String) uploadDocumentResponse.get("transientDocumentId");

    // Send an agreement using the transient document ID derived from above.
    DocumentIdentifierName idName = DocumentIdentifierName.TRANSIENT_DOCUMENT_ID;
    JSONObject sendAgreementResponse = RestApiAgreements.sendAgreement(accessToken, sendAgreementJSONFileName, transientDocumentId, idName);

    // Parse and read response.
    System.out.println("Agreement Sent. Agreement ID = " + sendAgreementResponse.get("agreementId"));
  }
}
