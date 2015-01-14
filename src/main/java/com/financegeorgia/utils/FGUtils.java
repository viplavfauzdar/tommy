/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import com.owlike.genson.Genson;
import org.apache.log4j.Logger;

/**
 *
 * @author Viplav
 */
public final class FGUtils {
    
    //Issue with logger since it doens't return the source class
    private static final Logger logger = Logger.getLogger(FGUtils.class);
    private final static Genson genson = new Genson();

    private FGUtils(){
        //prevent instantiation
    }
    
    public static Logger getLogger(){
        return logger;
    }
    
    public static void info(String src, String msg){
        logger.info(src + " - " + msg);
    }
    
    public static void debug(String src, String msg){
        if(logger.isDebugEnabled()) logger.debug(src + " - " + msg);
    }
    
    public static String toJson(Object obj){
        return genson.serialize(obj);
    }
}
