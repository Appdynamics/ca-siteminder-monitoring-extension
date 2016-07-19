package com.appdynamics.extensions.siteminder.metrics;


import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;


public class MetricPropertiesBuilder {

    public Map<String,MetricProperties> build(Map componentGlobalConfig){
        Map<String,MetricProperties> metricPropsMap = Maps.newHashMap();
        if(componentGlobalConfig == null || componentGlobalConfig.isEmpty()){
            return metricPropsMap;
        }
        String componentName = componentGlobalConfig.get("component").toString();
        List includeMetrics = (List)componentGlobalConfig.get("include");
        if(includeMetrics != null){
            for(Object metad : includeMetrics){
                Map localMetaData = (Map)metad;
                Map.Entry entry = (Map.Entry)localMetaData.entrySet().iterator().next();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                MetricProperties props = new DefaultMetricProperties();
                props.setKeyPrefix(componentName);
                props.setMetricName(key);
                setProps(componentGlobalConfig,props); //global level
                setProps(localMetaData, props); //local level
                metricPropsMap.put(value,props);
            }
        }
        return metricPropsMap;
    }

    private void setProps(Map metadata, MetricProperties props) {
        if(metadata.get("metricType") != null){
            props.setAggregationFields(metadata.get("metricType").toString());
        }
        if(metadata.get("multiplier") != null){
            props.setMultiplier(Double.parseDouble(metadata.get("multiplier").toString()));
        }
        if(metadata.get("convert") != null){
            props.setConversionValues((Map)metadata.get("convert"));
        }
        if(metadata.get("aggregation") != null){
            props.setAggregation(Boolean.parseBoolean(metadata.get("aggregation").toString()));
        }
        if(metadata.get("delta") != null){
            props.setDelta(Boolean.parseBoolean(metadata.get("delta").toString()));
        }
    }
}


