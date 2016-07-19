package com.appdynamics.extensions.siteminder;


public class OIDUtils {
    public static String getSuffixOfOID(String key) {
        int idx = key.lastIndexOf(".");
        if(idx == -1){
            return null;
        }
        return key.substring(idx + 1);
    }

    public static String getPrefixOfOID(String key) {
        int idx = key.lastIndexOf(".");
        if(idx == -1){
            return null;
        }
        return key.substring(0,idx);
    }
}
