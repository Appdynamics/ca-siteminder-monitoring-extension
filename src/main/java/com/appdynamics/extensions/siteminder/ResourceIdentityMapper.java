/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.appdynamics.extensions.siteminder.OIDUtils.getSuffixOfOID;


class ResourceIdentityMapper {

    private final SNMPWalker walker;

    ResourceIdentityMapper(SNMPWalker walker){
        this.walker = walker;
    }

    Map<String,Map> mapResources(Object resourceObj) throws IOException {
        Map<String,Map> resourceIdentityMap = Maps.newHashMap();
        if(resourceObj == null){
            return resourceIdentityMap;
        }
        @SuppressWarnings("unchecked")
        List<Map> resourceConfig = (List<Map>)resourceObj;
        for(Map componentConfig : resourceConfig){
            String componentName = componentConfig.get("component").toString();
            Map componentIdentityMetadata = (Map)componentConfig.get("include");
            String oid = extractOid(componentIdentityMetadata);
            if(oid != null){
                Map<String,Object> oidValueMap =  walker.walk(Lists.newArrayList(oid));
                //can have a convert map that maps resource ids to resource names
                Map convertMap = extractConvertMap(componentIdentityMetadata);
                Map identityMap = (convertMap != null) ? convertMap : convertToIdentityMap(oidValueMap);
                resourceIdentityMap.put(componentName,identityMap);
            }
        }
        return resourceIdentityMap;
    }

    private Map<String, Object> convertToIdentityMap(Map<String, Object> oidValueMap) {
        Map<String,Object> identityMap = Maps.newHashMap();
        for(Map.Entry<String,Object> entry : oidValueMap.entrySet()){
            String id = getSuffixOfOID(entry.getKey());
            if(id != null){
                identityMap.put(id,entry.getValue());
            }
        }
        return identityMap;
    }

    private String extractOid(Map compIdentityMetadata) {
        Map.Entry firstEntry = (Map.Entry) compIdentityMetadata.entrySet().iterator().next();
        return firstEntry.getValue().toString();
    }

    private Map extractConvertMap(Map componentIdentityMap) {
        if(componentIdentityMap.get("convert") != null){
            return (Map)componentIdentityMap.get("convert");
        }
        return null;
    }
}
