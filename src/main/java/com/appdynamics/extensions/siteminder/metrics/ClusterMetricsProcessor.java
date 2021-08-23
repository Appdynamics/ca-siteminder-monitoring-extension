/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder.metrics;

import com.appdynamics.extensions.logging.ExtensionsLoggerFactory;
import com.appdynamics.extensions.metrics.AggregatorFactory;
import com.appdynamics.extensions.metrics.AggregatorKey;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import org.slf4j.Logger;

import java.util.List;


public class ClusterMetricsProcessor {

    private static final Logger logger = ExtensionsLoggerFactory.getLogger(ClusterMetricsProcessor.class);
    static final String IND = "IND";
    static final String SUM = "SUM";

    public void collect(AggregatorFactory aggregatorFactory, List<Metric> metrics) {
        if(metrics == null){
            return;
        }
        for(Metric nodeMetric : metrics){
            if(nodeMetric.getProperties().isAggregation()){
                logger.debug("Aggregating metric {}",nodeMetric.getMetricName());
                String metricType = getMetricTypeInOtherFormat(nodeMetric.getProperties());
                AggregatorKey aggKey = new AggregatorKey(nodeMetric.getClusterKey() + "|" + nodeMetric.getMetricName(),metricType);
                aggregatorFactory.getAggregator(metricType).add(aggKey,nodeMetric.getMetricValue().toString());
            }
        }
    }

    private String getMetricTypeInOtherFormat(MetricProperties properties) {
        //converting it into a string so that commons lib can handle it.
        String str = properties.getAggregationType() + "." + properties.getTimeRollupType();
        if(properties.getClusterRollupType().equalsIgnoreCase(MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL)){
            return  str + "." + IND;
        }
        return str + "." + SUM;
    }

}
