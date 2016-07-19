package com.appdynamics.extensions.siteminder.metrics;

import com.appdynamics.extensions.util.AggregatorFactory;
import com.appdynamics.extensions.util.AggregatorKey;
import com.singularity.ee.agent.systemagent.api.MetricWriter;

import java.util.List;


public class ClusterMetricsProcessor {

    public void collect(AggregatorFactory aggregatorFactory, List<Metric> metrics) {
        if(metrics == null){
            return;
        }
        for(Metric nodeMetric : metrics){
            if(nodeMetric.getProperties().isAggregation()){
                String metricType = getClusterAggregationType(nodeMetric.getProperties());
                AggregatorKey aggKey = new AggregatorKey(nodeMetric.getMetricName(),metricType);
                aggregatorFactory.getAggregator(metricType).add(aggKey,nodeMetric.getMetricValue().toString());
            }
        }
    }

    private String getClusterAggregationType(MetricProperties properties) {
        if(properties.getClusterRollupType().equalsIgnoreCase(MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL)){
            return "a.a.IND"; //converting it into a string so that commons lib can handle it. Last string after the second . is the only important substring here
        }
        return "a.a.SUM";
    }

}
