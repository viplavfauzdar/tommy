/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import java.io.File;

/**
 *
 * @author VXF4071
 */

public class Path {
    
    private static final Path path = new Path();
    
    private Path(){}
    
    public static final Path getInstance(){
        return path;
    }
    
    public static final String fgDataDir = "FG";
    public static final String userHomeDir = System.getProperty("user.home");
    public static final String userWorkingDir = System.getProperty("user.dir");
    public static final String osName = System.getProperty("os.name");
    public static final String fileSep = System.getProperty("file.separator");
    public static final String openshiftDataDir = System.getenv().get("OPENSHIFT_DATA_DIR"); 
    
    public static String getUserHomeDir(){  
        File fgDir = new File(fgDataDir);
        if(!fgDir.exists()) fgDir.mkdir();
        if(openshiftDataDir!=null)
            return openshiftDataDir;
        else
            return userHomeDir;
    }

    public static void main(String[] args) {
//        System.out.println(System.getProperty("user.home"));
//        System.out.println(System.getProperty("user.dir"));
//        System.out.println(System.getProperty("os.arch"));
//        System.out.println(System.getProperty("os.name"));
//        System.out.println(System.getProperty("os.version"));
//        System.out.println(System.getProperty("file.separator"));
//        System.out.println(System.getProperty("user.name"));
//        System.out.println(System.getProperty("java.io.tmpdir"));
//
//        Map<String, String> env = System.getenv();
//        for (String envName : env.keySet()) {
//            System.out.format("%s=%s%n",
//                    envName,
//                    env.get(envName));
//
//        }
//
//        System.out.println(env.get("OPENSHIFT_DATA_DIR"));
        
        Path.getInstance();
        System.out.println(Path.fileSep);
    }

}
