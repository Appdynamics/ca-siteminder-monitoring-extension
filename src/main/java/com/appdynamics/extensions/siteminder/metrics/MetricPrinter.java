/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder.metrics;


import com.appdynamics.extensions.logging.ExtensionsLoggerFactory;
import com.appdynamics.extensions.metrics.Aggregator;
import com.appdynamics.extensions.metrics.AggregatorFactory;
import com.appdynamics.extensions.metrics.AggregatorKey;
import com.appdynamics.extensions.MetricWriteHelper;
import com.google.common.base.Strings;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.appdynamics.extensions.siteminder.Util.split;
import static com.appdynamics.extensions.siteminder.Util.toBigIntString;

public class MetricPrinter {

    private static final Logger logger = ExtensionsLoggerFactory.getLogger(MetricPrinter.class);

    private int totalMetricsReported;
    private String metricPrefix;
    private String displayName;
    private MetricWriteHelper metricWriter;

    public MetricPrinter(String metricPrefix, String displayName, MetricWriteHelper metricWriter){
        this.metricPrefix = metricPrefix;
        this.displayName = displayName;
        this.metricWriter = metricWriter;
    }

    public void reportComponentMetrics(final List<Metric> componentMetrics) {
        if(componentMetrics == null || componentMetrics.isEmpty()){
            return;
        }
        for(Metric metric : componentMetrics){
            MetricProperties props = metric.getProperties();
            String fullMetricPath = formMetricPath(metric.getMetricKey());
            printMetric(fullMetricPath,metric.getMetricValue(), props.getAggregationType(),props.getTimeRollupType(),props.getClusterRollupType());
        }
    }

    public void reportClusterLevelMetrics(final AggregatorFactory aggregatorFactory) {
        Collection<Aggregator<AggregatorKey>> aggregators = aggregatorFactory.getAggregators();
        for (Aggregator<AggregatorKey> aggregator : aggregators) {
            Set<AggregatorKey> keys = aggregator.keys();
            for (AggregatorKey key : keys) {
                BigDecimal value = aggregator.getAggregatedValue(key);
                String path = formMetricPath(key.getMetricPath());
                String[] splits = split(key.getMetricType(),"\\.");
                logger.debug("Reported Aggregated metric {} with value {} and with type {}",path,value, Arrays.toString(splits));
                if(splits.length == 3){
                    String clusterAgg = splits[2].equals(ClusterMetricsProcessor.IND) ? MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL : MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE;
                    printMetric(path, value, splits[0],splits[1],clusterAgg);
                }
            }
        }
    }


    public void printMetric(String metricPath, BigDecimal metricValue, String aggType, String timeRollupType, String clusterRollupType) {
        try{
            String metricValStr = toBigIntString(metricValue);
            if(metricValStr != null) {
                metricWriter.printMetric(metricPath,metricValStr,aggType,timeRollupType,clusterRollupType);
                //  System.out.println("Sending [" + aggType + METRICS_SEPARATOR + timeRollupType + METRICS_SEPARATOR + clusterRollupType
                //  		+ "] metric = " + metricPath + " = " + metricValue);
                logger.debug("Sending [{}|{}|{}] metric= {},value={}", aggType, timeRollupType, clusterRollupType, metricPath, metricValue);
                totalMetricsReported++;
            }
        }
        catch (Exception e){
            logger.error("Error reporting metric {} with value {}",metricPath,metricValue,e);
        }
    }

    public String formMetricPath(String metricKey) {
        if(!Strings.isNullOrEmpty(displayName)){
            return metricPrefix + "|" + displayName + "|" + metricKey;
        }
        return metricPrefix + "|" + metricKey;
    }

    public int getTotalMetricsReported() {
        return totalMetricsReported;
    }
}
