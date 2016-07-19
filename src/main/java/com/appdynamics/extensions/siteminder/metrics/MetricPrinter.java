package com.appdynamics.extensions.siteminder.metrics;


import com.appdynamics.extensions.util.Aggregator;
import com.appdynamics.extensions.util.AggregatorFactory;
import com.appdynamics.extensions.util.AggregatorKey;
import com.appdynamics.extensions.util.MetricWriteHelper;
import com.google.common.base.Strings;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.appdynamics.extensions.siteminder.Util.split;
import static com.appdynamics.extensions.siteminder.Util.toBigIntString;

public class MetricPrinter {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MetricPrinter.class);

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
            printMetric(metric.getMetricKey(),metric.getMetricValue(), props.getAggregationType(),props.getTimeRollupType(),props.getClusterRollupType());
        }
    }

    public void reportClusterLevelMetrics(final AggregatorFactory aggregatorFactory, String componentName, final List<Metric> componentMetrics) {
        if(componentMetrics == null || componentMetrics.isEmpty()){
            return;
        }
        String clusterMetricKeyPrefix = formMetricPath(componentName);
        Collection<Aggregator<AggregatorKey>> aggregators = aggregatorFactory.getAggregators();
        for (Aggregator<AggregatorKey> aggregator : aggregators) {
            Set<AggregatorKey> keys = aggregator.keys();
            for (AggregatorKey key : keys) {
                BigDecimal value = aggregator.getAggregatedValue(key);
                String[] splits = split(key.getMetricType()," ");
                printMetric(clusterMetricKeyPrefix + "|" + key.getMetricPath(), value, splits[0],splits[1],splits[2]);
            }
        }

    }

    public void printMetric(String metricPath, BigDecimal metricValue, String aggType, String timeRollupType, String clusterRollupType) {
        String fullMetricPath = formMetricPath(metricPath);
        try{
            String metricValStr = toBigIntString(metricValue);
            if(metricValStr != null) {
                metricWriter.printMetric(fullMetricPath,metricValStr,aggType,timeRollupType,clusterRollupType);
                //  System.out.println("Sending [" + aggType + METRICS_SEPARATOR + timeRollupType + METRICS_SEPARATOR + clusterRollupType
                //  		+ "] metric = " + metricPath + " = " + metricValue);
                logger.debug("Sending [{}|{}|{}] metric= {},value={}", aggType, timeRollupType, clusterRollupType, metricPath, metricValue);
                totalMetricsReported++;
            }
        }
        catch (Exception e){
            logger.error("Error reporting metric {} with value {}",fullMetricPath,metricValue,e);
        }
    }

    public String formMetricPath(String metricKey) {
        if(!Strings.isNullOrEmpty(displayName)){
            return metricPrefix + displayName + "|" + metricKey;
        }
        return metricPrefix + metricKey;
    }

    public int getTotalMetricsReported() {
        return totalMetricsReported;
    }
}
