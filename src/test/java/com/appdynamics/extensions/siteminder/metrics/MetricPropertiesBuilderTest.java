/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder.metrics;

import com.appdynamics.extensions.yml.YmlReader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetricPropertiesBuilderTest {

    MetricPropertiesBuilder builder = new MetricPropertiesBuilder();

    @Test
    public void whenNoComponentConfig_thenReturnEmptyMap(){
        Map<String,MetricProperties> propsMap = builder.build(null);
        Assert.assertTrue(propsMap.size() == 0);
    }

    @Test
    public void whenEmptyComponentConfig_thenReturnEmptyMap(){
        Map<String,MetricProperties> propsMap = builder.build(Maps.newHashMap());
        Assert.assertTrue(propsMap.size() == 0);
    }

    @Test
    public void whenConfigWithGlobalPropsNoLocalOverrides_thenSetProps(){
        Map configMap = YmlReader.readFromFileAsMap(new File(this.getClass().getResource("/conf/config_with_global_props.yml").getFile()));
        Map instance = (Map)((List)configMap.get("instances")).get(0);
        List<Map> componentConfigs = (List<Map>)instance.get("metrics");
        Map<String,MetricProperties> propsMap = builder.build(componentConfigs.get(0));
        MetricProperties props = propsMap.get("1.3.6.1.4.1.2552.200.300.1.3.1.17");
        Assert.assertTrue(props.getAggregationType().equals("SUM"));
        Assert.assertTrue(props.getTimeRollupType().equals("SUM"));
        Assert.assertTrue(props.getClusterRollupType().equals("INDIVIDUAL"));
        Assert.assertTrue(props.getKeyPrefix().equals("policyServer"));
        Assert.assertTrue(props.getMultiplier() == 10);
        Assert.assertTrue(props.isDelta());
        Assert.assertTrue(props.getMetricName().equals("policyServerMaxSockets"));
    }

    @Test
    public void whenConfigWithLocalOverridesNoGlobalProps_thenSetProps(){
        Map configMap = YmlReader.readFromFileAsMap(new File(this.getClass().getResource("/conf/config_with_local_overrides.yml").getFile()));
        Map instance = (Map)((List)configMap.get("instances")).get(0);
        List<Map> componentConfigs = (List<Map>)instance.get("metrics");
        Map<String,MetricProperties> propsMap = builder.build(componentConfigs.get(0));
        MetricProperties props = propsMap.get("1.3.6.1.4.1.2552.200.300.1.3.1.5");
        Assert.assertTrue(props.getAggregationType().equals("AVERAGE"));
        Assert.assertTrue(props.getTimeRollupType().equals("AVERAGE"));
        Assert.assertTrue(props.getClusterRollupType().equals("INDIVIDUAL"));
        Assert.assertTrue(props.getKeyPrefix().equals("policyServer"));
        Assert.assertTrue(props.isDelta());
        Assert.assertTrue(props.getMetricName().equals("policyServerStatus"));
        Assert.assertTrue(props.getConversionValues().get("$default").equals("0"));
    }

    @Test
    public void whenConfigWithGlobalPropsAndLocalOverrides_thenSetProps(){
        Map configMap = YmlReader.readFromFileAsMap(new File(this.getClass().getResource("/conf/config_with_global_and_local_overrides.yml").getFile()));
        Map instance = (Map)((List)configMap.get("instances")).get(0);
        List<Map> componentConfigs = (List<Map>)instance.get("metrics");
        Map<String,MetricProperties> propsMap = builder.build(componentConfigs.get(0));
        MetricProperties props = propsMap.get("1.3.6.1.4.1.2552.200.300.1.3.1.17");
        Assert.assertTrue(props.getAggregationType().equals("SUM"));
        Assert.assertTrue(props.getTimeRollupType().equals("SUM"));
        Assert.assertTrue(props.getClusterRollupType().equals("INDIVIDUAL"));
        Assert.assertTrue(props.getKeyPrefix().equals("policyServer"));
        Assert.assertTrue(props.getMultiplier() == 10);
        Assert.assertTrue(props.isDelta());
        Assert.assertTrue(props.getMetricName().equals("policyServerMaxSockets"));
        MetricProperties props1 = propsMap.get("1.3.6.1.4.1.2552.200.300.1.3.1.22");
        Assert.assertTrue(props1.getAggregationType().equals("AVERAGE"));
        Assert.assertTrue(props1.getTimeRollupType().equals("AVERAGE"));
        Assert.assertTrue(props1.getClusterRollupType().equals("COLLECTIVE"));
        Assert.assertTrue(props1.getKeyPrefix().equals("policyServer"));
        Assert.assertFalse(props1.isDelta());
        Assert.assertTrue(props1.getMetricName().equals("policyServerAzRejectCount"));
        Assert.assertTrue(props1.getMultiplier() == 10);
        Assert.assertTrue(props1.getConversionValues() == null);
    }

    @Test
    public void whenConfigWithNoGlobalPropsAndNoLocalOverrides_thenSetProps(){
        Map configMap = YmlReader.readFromFileAsMap(new File(this.getClass().getResource("/conf/config_with_no_global_props_no_local_overrides.yml").getFile()));
        Map instance = (Map)((List)configMap.get("instances")).get(0);
        List<Map> componentConfigs = (List<Map>)instance.get("metrics");
        Map<String,MetricProperties> propsMap = builder.build(componentConfigs.get(0));
        MetricProperties props = propsMap.get("1.3.6.1.4.1.2552.200.300.1.3.1.17");
        Assert.assertTrue(props.getAggregationType().equals("AVERAGE"));
        Assert.assertTrue(props.getTimeRollupType().equals("AVERAGE"));
        Assert.assertTrue(props.getClusterRollupType().equals("INDIVIDUAL"));
        Assert.assertTrue(props.getKeyPrefix().equals("policyServer"));
        Assert.assertTrue(props.getMultiplier() == 1);
        Assert.assertFalse(props.isDelta());
        Assert.assertFalse(props.isAggregation());
        Assert.assertTrue(props.getMetricName().equals("policyServerMaxSockets"));
    }
}
