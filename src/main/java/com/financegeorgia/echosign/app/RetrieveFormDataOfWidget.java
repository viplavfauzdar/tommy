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

import com.financegeorgia.echosign.util.FileUtils;
import com.financegeorgia.echosign.util.RestApiTokens;
import com.financegeorgia.echosign.util.RestApiWidgets;
import com.financegeorgia.echosign.util.RestError;

/**
 * This sample client demonstrates how to download the form data of the specified widget.
 * 
 * <p>
 * <b>IMPORTANT</b>:
 * <ol>
 * <li>Before running this sample, check that you have modified the JSON file 'Auth.json' with appropriate values. Which values need to be
 * specified is indicated in the file.</li>
 * <li>Make sure that you have specified a valid widget ID in <code>widgetId</code> below.</li>
 * <li>Check that the default output location in the field <code>OUTPUT_PATH</code> of FileUtils.java is suitable.</li>
 * </ol>
 * </p>
 */
public class RetrieveFormDataOfWidget {
  // TODO: Enter a valid widget ID. Please refer to the "widgets" end-point in the API documentation to learn how to obtain IDs of
  // widgets present in EchoSign.
  private static String widgetId = "2AAABLblqZhAz4toO6V1WUMR7KtdgNRT6oZbMpIIjjL6Yd26wfgcQKLz72ORcsnMDMKMEwltEi24*";

  // File containing request body to get an access token.
  private static final String authRequestJSONFileName = "Auth.json";

  /**
   * Entry point for this sample client program.
   */
  public static void main(String args[]) {
    try {
      RetrieveFormDataOfWidget client = new RetrieveFormDataOfWidget();
      client.run();
    }
    catch (Exception e) {
      System.err.println("Error while retrieving form data for widget");
      e.printStackTrace();
    }
  }

  /**
   * Main work function. See the class comment for details.
   */
  private void run() throws Exception {
    // Get access token to make further API calls.
    String accessToken = RestApiTokens.getAccessToken(authRequestJSONFileName);
    
    // Display name of the widget associated with the specified widget ID.
    JSONObject widgetInfo = RestApiWidgets.getWidgetInfo(accessToken, widgetId);
    String widgetName = (String) widgetInfo.get("name");
    System.out.println("Widget name: " + widgetName);
    
    String fileName = widgetName + "_" + System.currentTimeMillis() + ".csv";
    System.out.println("Saving form data '" + fileName + "'");
    
    // Make API call to get form data of this widget.
    byte formDataStream[] = RestApiWidgets.getFormdataForWidget(accessToken, widgetId);

    if (formDataStream != null) {
      boolean success = FileUtils.saveToFile(formDataStream, FileUtils.FORM_DATA_OUTPUT_PATH, fileName);
      if (success)
        System.out.println("Successfully saved form data in '" + FileUtils.FORM_DATA_OUTPUT_PATH + "'.");
      else
        System.err.println(RestError.FILE_NOT_SAVED.errMessage);
    }
    else {
      System.err.println("Error while retrieving form data for widget: " + widgetName);
    }
  }
}
