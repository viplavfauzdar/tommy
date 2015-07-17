/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.echosign.exec;

import java.io.File;
import java.io.IOException;
import java.util.List;
 
/**
 *
 * @author viplav
 */
public class MultipartFileUploader {
    
    public static void main(String[] args) {
        String charset = "UTF-8";
        File uploadFile1 = new File("C:/checklist_accinvestor.docx");
        //File uploadFile2 = new File("e:/Test/PIC2.JPG");
        String requestURL = "https://api.echosign.com/api/rest/v3/transientDocuments";
 
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
            multipart.addHeaderField("Access-Token", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");
             
            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");
             
            multipart.addFilePart("fileUpload", uploadFile1);
            //multipart.addFilePart("fileUpload", uploadFile2);
 
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
