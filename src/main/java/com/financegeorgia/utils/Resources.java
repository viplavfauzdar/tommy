/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import java.util.ResourceBundle;

/**
 *
 * @author Viplav
 */
public class Resources {

    private static final Resources res = new Resources();

    private static ResourceBundle resource = null;
    private static String file = "";

    private Resources() {
    }

    public static Resources getInstance() {
        return res;
    }

    public void setResourceFile(String file) {
        this.file = file;
    }

    public String getResource(String name) {
        resource = ResourceBundle.getBundle("com.financegeorgia.res." + file);
        return resource.getString(name);
    }

    public static void main(String[] args) {
        Resources r = Resources.getInstance();
        r.setResourceFile("sendmail");
        System.out.println(r.getResource("contactSubject"));

    }

}
