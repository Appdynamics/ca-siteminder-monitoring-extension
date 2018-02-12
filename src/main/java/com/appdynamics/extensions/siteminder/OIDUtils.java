/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

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
