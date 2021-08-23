/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder.metrics;


import com.appdynamics.extensions.logging.ExtensionsLoggerFactory;
import com.appdynamics.extensions.siteminder.SNMPWalker;
import com.google.common.collect.Lists;
import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.appdynamics.extensions.siteminder.OIDUtils.getPrefixOfOID;
import static com.appdynamics.extensions.siteminder.OIDUtils.getSuffixOfOID;

public class ComponentMetricsProcessor {

    private static final Logger logger = ExtensionsLoggerFactory.getLogger(ComponentMetricsProcessor.class);

    private final MetricValueTransformer transformer = new MetricValueTransformer();

    public List<Metric> collect(String componentName, SNMPWalker walker, Map<String,Object> identityMap, Map<String, MetricProperties> metricProps) throws IOException {
        Map<String,Object> componentMetricsMap = walker.walk(metricProps.keySet());
        List<Metric> compMetrics = Lists.newArrayList();
        for(Map.Entry entryMetricMap : componentMetricsMap.entrySet()){
            String key = (String)entryMetricMap.getKey();
            Object value = entryMetricMap.getValue();
            logger.debug("Processing for oid.. {}",key);
            String prefixOid = getPrefixOfOID(key);
            logger.debug("Prefix oid.. {}",prefixOid);
            String resourceOid = getSuffixOfOID(key);
            logger.debug("Suffix oid.. {}",resourceOid);
            if(prefixOid != null && resourceOid != null){
                MetricProperties props = metricProps.get(prefixOid);
                if(props != null){
                    String metricPath = getMetricPath(props,resourceOid,identityMap);
                    BigDecimal metricValue = transformer.transform(metricPath,value,props);
                    if(metricValue != null){
                        Metric m = new Metric();
                        m.setMetricValue(metricValue);
                        m.setMetricKey(metricPath);
                        m.setClusterKey(componentName);
                        m.setMetricName(props.getMetricName());
                        m.setProperties(props);
                        compMetrics.add(m);
                    }
                }
                else{
                    logger.error("Cannot find metrics props for {}",prefixOid);
                }
            }
            else{
               logger.error("Invalid OID %s for component %s",key,componentName);

            }
        }
        return compMetrics;
    }

    private String getMetricPath(MetricProperties props, String resourceOid, Map<String, Object> identityMap) {
        StringBuilder builder = new StringBuilder();
        builder.append(props.getKeyPrefix()).append("|");
        if(identityMap.get(resourceOid) != null){
            String resource = identityMap.get(resourceOid).toString();
            builder.append(resource).append("|");
        }
        else{
            builder.append(resourceOid).append("|");
        }
        builder.append(props.getMetricName());
        return builder.toString();
    }

}
