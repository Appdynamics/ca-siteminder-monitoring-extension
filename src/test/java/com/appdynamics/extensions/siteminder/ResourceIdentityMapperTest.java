/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.appdynamics.extensions.yml.YmlReader;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceIdentityMapperTest {

    @Mock
    SNMPWalker walker;

    @Test
    public void whenResourceIdentiyConfigIsNull_thenReturnEmptyMap() throws IOException {
        ResourceIdentityMapper rim = new ResourceIdentityMapper(walker);
        Assert.assertTrue(rim.mapResources(null).size() == 0);
    }

    @Test
    public void whenValidResourceIdentityWithConvert_thenReturnIdentityMap() throws IOException {
        Map configMap = YmlReader.readFromFileAsMap(new File(this.getClass().getResource("/conf/config_with_resource_identity_map.yml").getFile()));
        Map<String,Object> oidValueMap = Maps.newHashMap();
        oidValueMap.put("1.3.6.1.4.1.2552.200.300.1.1.1.3.2","abc.local");
        oidValueMap.put("1.3.6.1.4.1.2552.200.300.1.1.1.3.34","kucku.local");
        when(walker.walk(anyList())).thenReturn(oidValueMap);
        Map instance = (Map)((List)configMap.get("instances")).get(0);
        List<Map> resourceConfigs = (List<Map>)instance.get("resources");
        ResourceIdentityMapper rim = new ResourceIdentityMapper(walker);
        Map<String,Map> identityMap = rim.mapResources(resourceConfigs);
        Assert.assertTrue(identityMap.size() == oidValueMap.size());
        Map policyServerMap = (Map)identityMap.get("policyServer");
        Map webagentMap = (Map)identityMap.get("webagents");
        Assert.assertTrue(policyServerMap.get("2").equals(oidValueMap.get("1.3.6.1.4.1.2552.200.300.1.1.1.3.2")));
        Assert.assertTrue(policyServerMap.get("34").equals(oidValueMap.get("1.3.6.1.4.1.2552.200.300.1.1.1.3.34")));
        Assert.assertTrue(webagentMap.get("9").equals("agentA"));
        Assert.assertTrue(webagentMap.get("7").equals("agentB"));
    }

}
