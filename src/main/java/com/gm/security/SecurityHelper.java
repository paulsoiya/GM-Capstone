package com.gm.security;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityHelper {
    
    public SecurityHelper() { } 
    
    public String md5(String str){
         
       String result = "";
       try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(Charset.forName("UTF8")), 0, 
                      str.length());
            result = new BigInteger(1,md.digest()).toString(16);
           
       }catch(Exception e){
           e.printStackTrace();
       }
       
       return result;
    }
    
    
}
