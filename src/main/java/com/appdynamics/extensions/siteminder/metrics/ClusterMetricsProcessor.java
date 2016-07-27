package com.appdynamics.extensions.siteminder.metrics;

import com.appdynamics.extensions.util.AggregatorFactory;
import com.appdynamics.extensions.util.AggregatorKey;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import org.slf4j.LoggerFactory;

import java.util.List;


public class ClusterMetricsProcessor {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ClusterMetricsProcessor.class);
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
