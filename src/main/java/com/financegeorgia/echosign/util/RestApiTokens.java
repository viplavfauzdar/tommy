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

package com.financegeorgia.echosign.util;

import java.util.HashMap;

import org.json.simple.JSONObject;

/**
 * Encapsulates calls to REST end points related to access token.
 * 
 * <p>
 * <b>IMPORTANT</b>: Before running this sample, check that you have modified the JSON file 'Auth.json' with appropriate values. Which
 * values need to be specified is indicated in the file.
 * </p>
 */
public class RestApiTokens {
  private final static String TOKEN_ENDPOINT = "/tokens";

  /**
   * Fetches the access token for the specified user.
   * 
   * @param requestJSONFile reference to the file containing template request JSON required for this API invocation.
   * @return access token.
   * @throws Exception
   */
  public static String getAccessToken(String requestJSONFile) throws Exception {
    // URL for token end point.
    String url = RestApiUtils.AUTH_BASE_URL + TOKEN_ENDPOINT;

    // Create header list.
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(RestApiUtils.HttpHeaderField.CONTENT_TYPE.toString(), RestApiUtils.MimeType.JSON.toString());

    // Get request JSON from the specified file.
    JSONObject requestBody = FileUtils.getRequestJson(requestJSONFile);

    // Invoke API and get JSON response.
    JSONObject responseJSON = null;
    responseJSON = (JSONObject) RestApiUtils.makeApiCall(url, RestApiUtils.HttpRequestMethod.POST, headers, requestBody.toString());
    
    // Extract and return access token from the JSON response.
    String token = (String) responseJSON.get("accessToken");
    return token;
  }
}
