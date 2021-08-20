/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder.metrics;


import com.appdynamics.extensions.siteminder.SNMPWalker;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComponentMetricsProcessorTest {

    @Mock
    SNMPWalker snmpWalker;

    ComponentMetricsProcessor processor = new ComponentMetricsProcessor();

    @Test
    public void whenUnMatchedMetricProps_thenMetricIsDropped() throws IOException {
        Map<String, Object> identityMap = createIdentityMap();
        Map<String,MetricProperties> propsMap = Maps.newHashMap();
        propsMap.put("1.3.6.1.4.1.2552.200.300.1.3.1.5",new DefaultMetricProperties());
        Map<String,Object> componentMetricsMap = createMetricsMap();
        when(snmpWalker.walk(propsMap.keySet())).thenReturn(componentMetricsMap);
        List<Metric> metricList = processor.collect("policyServer",snmpWalker,identityMap,propsMap);
        Assert.assertTrue(metricList.isEmpty());
    }

    @Test
    public void whenMetricValueIsNull_thenMetricIsDropped() throws IOException {
        Map<String, Object> identityMap = createIdentityMap();
        Map<String,MetricProperties> propsMap = Maps.newHashMap();
        MetricProperties prop = new DefaultMetricProperties();
        prop.setDelta(true);
        propsMap.put("1.3.6.1.4.1.2552.200.300.1.3.1.5",prop);
        Map<String,Object> componentMetricsMap = createMetricsMap();
        when(snmpWalker.walk(propsMap.keySet())).thenReturn(componentMetricsMap);
        List<Metric> metricList = processor.collect("policyServer",snmpWalker,identityMap,propsMap);
        Assert.assertTrue(metricList.isEmpty());
    }

    @Test
    public void whenSomeMatchesForIdentityMap_thenMetricIsAddedWithAppropriateProps() throws IOException {
//        Map<String, Object> identityMap = createIdentityMap();
//        Map<String,MetricProperties> propsMap = Maps.newHashMap();
//        final MetricProperties prop1 = new DefaultMetricProperties();
//        prop1.setMetricName("policyServerMaxSockets");
//        String component = "policyServer";
//        prop1.setKeyPrefix(component);
//        propsMap.put("1.3.6.1.4.1.2552.200.300.1.3.1",prop1);
//        final MetricProperties prop2 = new DefaultMetricProperties();
//        prop2.setMetricName("policyServerAzAcceptCount");
//        prop2.setKeyPrefix(component);
//        propsMap.put("1.3.6.1.4.1.2552.200.300.1.3.2",prop2);
//        Map<String,Object> componentMetricsMap = createMetricsMap();
//        when(snmpWalker.walk(propsMap.keySet())).thenReturn(componentMetricsMap);
//        List<Metric> metricList = processor.collect(component,snmpWalker,identityMap,propsMap);
//        Assert.assertTrue(metricList.size() == 4);
//        Predicate<Metric> metricPredicate = new Predicate<Metric>() {
//            @Override
//            public boolean apply(Metric input) {
//                return input.getMetricName().equals(prop1.getMetricName());
//            }
//        };
//        Collection<Metric> predList = Collections2.filter(metricList,metricPredicate);
//        Assert.assertTrue(predList.size() == 2);
//        Predicate<Metric> withIdMapped = new Predicate<Metric>() {
//            @Override
//            public boolean apply(Metric input) {
//                return input.getMetricKey().equals("policyServer|7|policyServerMaxSockets");
//            }
//        };
//        Collection<Metric> withIdMappedList = Collections2.filter(metricList,withIdMapped);
//        Assert.assertTrue(withIdMappedList.size() == 1);
//        Predicate<Metric> withoutIdMapped = new Predicate<Metric>() {
//            @Override
//            public boolean apply(Metric input) {
//                return input.getMetricKey().equals("policyServer|policyServerA|policyServerMaxSockets");
//            }
//        };
//        Collection<Metric> withoutIdMappedList = Collections2.filter(metricList,withoutIdMapped);
//        Assert.assertTrue(withoutIdMappedList.size() == 1);
    }

    private Map<String, Object> createMetricsMap() {
        Map<String,Object> metricsMap = Maps.newHashMap();
        metricsMap.put("1.3.6.1.4.1.2552.200.300.1.3.1.2",12);
        metricsMap.put("1.3.6.1.4.1.2552.200.300.1.3.1.7",22);
        metricsMap.put("1.3.6.1.4.1.2552.200.300.1.3.2.6",32);
        metricsMap.put("1.3.6.1.4.1.2552.200.300.1.3.2.4",42);
        return metricsMap;
    }

    private Map<String, Object> createIdentityMap() {
        Map<String,Object> identityMap = Maps.newHashMap();
        identityMap.put("2","policyServerA");
        identityMap.put("3","policyServerB");
        identityMap.put("4","policyServerC");
        return identityMap;
    }
}
