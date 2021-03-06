/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import org.apache.log4j.Logger;

/**
 *
 * @author Viplav
 */
public class FGException extends RuntimeException{ //use runtime instead as no need for throws in calling method
    
    private static final Logger logger = Logger.getLogger(FGException.class);
    
    public FGException(String msg){
        super(msg);
        logger.fatal(msg);
    }
    
    public FGException(Exception e){               
        super(e);
        logger.fatal(e);
    }
    
}
