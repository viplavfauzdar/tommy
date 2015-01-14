/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;
/**
 *
 * @author Viplav
 */ 

public class SaltedHash {
    
    private static Logger logger = Logger.getLogger(SaltedHash.class);
    
    private SaltedHash(){
        ;
    }
    
    private static SaltedHash saltedHash = new SaltedHash();
    
    public static SaltedHash getInstance(){
        return saltedHash;
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String passwordToHash = "abc123";
        
        SaltedHash s = SaltedHash.getInstance();
         
        String securePassword = s.get_SHA_1_SecurePassword(passwordToHash);
        logger.info(securePassword);
         
        securePassword = s.get_SHA_256_SecurePassword(passwordToHash);
        logger.info(securePassword);
         
        securePassword = s.get_SHA_384_SecurePassword(passwordToHash);
        logger.info(securePassword);
         
        securePassword = s.get_SHA_512_SecurePassword(passwordToHash);
        logger.info(securePassword);
        System.out.println(securePassword);
    }
 
    
    
    private static String get_SHA_SecurePassword(String algo, String passwordToHash)
    {
        String generatedPassword = null;        
        try {
            String salt = getSalt();
            
            MessageDigest md = MessageDigest.getInstance(algo);
            //md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.fatal(e);
        }
        return generatedPassword;
    }
    
    public String get_SHA_1_SecurePassword(String passwordToHash){
        return get_SHA_SecurePassword("SHA-1", passwordToHash);
    }
    
    public String get_SHA_256_SecurePassword(String passwordToHash)
    {        
        return get_SHA_SecurePassword("SHA-256", passwordToHash);
    }
     
    public String get_SHA_384_SecurePassword(String passwordToHash)
    {
        return get_SHA_SecurePassword("SHA-384", passwordToHash);
    }
     
    public String get_SHA_512_SecurePassword(String passwordToHash)
    {
     return get_SHA_SecurePassword("SHA-512", passwordToHash);
    }
     
    //Add salt
    private static String getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
}