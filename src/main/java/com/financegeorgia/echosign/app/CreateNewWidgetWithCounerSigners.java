/*************************************************************************
 * ADOBE SYSTEMS INCORPORATED
 * Copyright 2014 Adobe Systems Incorporated
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
import com.financegeorgia.echosign.util.RestApiWidgets;
import org.apache.log4j.Logger;

/**
 * This sample client demonstrates how to create a new widget.
 * 
 * <p>
 * <b>IMPORTANT</b>: Before running this sample, check that you have modified the JSON files 'Auth.json' and 'CreateWidget.json' with
 * appropriate values. Which values need to be specified is indicated in the files.
 * </p>
 */
public class CreateNewWidgetWithCounerSigners {
    
  private static Logger logger = Logger.getLogger(CreateNewWidgetWithCounerSigners.class);

  // File containing request body to get an access token.
  private static final String authRequestJSONFileName = "Auth.json";
  
  // File containing request body for creating a widget.
  private final static String createWidgetJSONFileName = "CreateWidget.json";
  
  //Name of the file to be uploaded for sending an agreement.
  // TODO: Specify file name of choice here. The file must exist in the "requests" sub-package.
  private static String fileToBeUploaded = "checklist_accinvestor.pdf";
  
  //Name of the file from which form fields are to be extracted.
  // TODO: Specify file name of choice here. The file must exist in the "requests" sub-package.
  private static final String fileforFormFields = "DocumentWithFormFields.pdf";
  
  // Mime-type of the file being uploaded.
  // TODO: Change this depending on actual file used.
  private static final String mimeType = RestApiUtils.MimeType.PDF.toString();
  
  // Name to be given to the file after uploading it.
  // TODO: Specify a file name of choice, ensuring that its name consists only of characters in the ASCII character set (given this basic
  // sample implementation).
  private static final String uploadedFileName = "UploadedSample.pdf";
  
  // Name to be given to the file containing form fields.
  // TODO: Specify a file name of choice, ensuring that its name consists only of characters in the ASCII character set (given this basic
  // sample implementation).
  private static final String formFieldsFileName = "formFieldsSample.pdf";
  
  private static String widgetJS = null;
  private static String widgetURL = null;
  private static String userId = null, docName = null, callbackURL = null;
  
  public void setfileToBeUploaded(String fileToBeUploaded){
      this.fileToBeUploaded = fileToBeUploaded;
  }
  
  public void setUserId(String userId){
      this.userId = userId;
  }
  
  public void setDocName(String docName){
      this.docName = docName;
  }
  
  public void setCallbackURL(String callbackURL){
      this.callbackURL = callbackURL;
  }

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      logger.info("Starting widget creation!");
      CreateNewWidgetWithCounerSigners client = new CreateNewWidgetWithCounerSigners();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Failure in creating a new widget for the user.");
      e.printStackTrace();
    }
  }

  /**
   * Main work function. See the class comment for details.
   */
  public void run() throws Exception {
      
      logger.info("Starting widget creation!");
      
    // Get access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);
    
    // Upload a transient document and retrieve transient document ID from the response.
    JSONObject uploadDocumentResponse = RestApiAgreements.postTransientDocument(accessToken, mimeType, fileToBeUploaded, uploadedFileName);
    String transientDocumentId = (String) uploadDocumentResponse.get("transientDocumentId");
    
    // Upload a transient document with form fields to be used for extracting form fields for the created widget.
    JSONObject uploadFormFieldDocumentResponse = RestApiAgreements.postTransientDocument(accessToken, mimeType, fileforFormFields, formFieldsFileName);
    String formFieldDocumentId = (String) uploadFormFieldDocumentResponse.get("transientDocumentId");

    // Associate the identifiers with the uploaded documents.
    DocumentIdentifierName idName = DocumentIdentifierName.TRANSIENT_DOCUMENT_ID;
    DocumentIdentifierName formFieldIdName = DocumentIdentifierName.TRANSIENT_DOCUMENT_ID;    
    
    // Make API call to create new widget
    JSONObject widget = RestApiWidgets.createWidget(accessToken, createWidgetJSONFileName, transientDocumentId, idName, null, null, userId, docName, callbackURL);//formFieldDocumentId, formFieldIdName);

    System.out.println(widget);
    // Display widget ID and corresponding code of newly created widget.
    System.out.println("Newly created widget's ID: " + widget.get("widgetId"));
    System.out.println("The corresponding Javascript code to embed the created widget: " + widget.get("javascript") + "\n" + "OR \n" + "URL to host the widget: " + widget.get("url"));
//    return "Newly created widget's ID: " + widget.get("widgetId") + "<p>" +
//            "The corresponding Javascript code to embed the created widget: " + "<p>" +
//            widget.get("javascript") + "<p>" + 
//            "OR URL to host the widget: " + widget.get("url");
    widgetJS = widget.get("javascript").toString();
    widgetURL = widget.get("url").toString();
  }
  
  public String getWidgetJS(){
      return widgetJS;
  }
  
  public String getWidgetURL(){
      return widgetURL;
  }
}
