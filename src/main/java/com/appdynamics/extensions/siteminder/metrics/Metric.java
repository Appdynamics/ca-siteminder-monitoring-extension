package com.appdynamics.extensions.siteminder.metrics;

import java.math.BigDecimal;

public class Metric {
    private String metricName;
    private String clusterKey;
    private String metricKey;
    private BigDecimal metricValue;
    private MetricProperties properties;

    public String getClusterKey() {
        return clusterKey;
    }

    public void setClusterKey(String clusterKey) {
        this.clusterKey = clusterKey;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getMetricKey() {
        return metricKey;
    }

    public void setMetricKey(String metricKey) {
        this.metricKey = metricKey;
    }

    public BigDecimal getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(BigDecimal metricValue) {
        this.metricValue = metricValue;
    }

    public MetricProperties getProperties() {
        return properties;
    }

    public void setProperties(MetricProperties properties) {
        this.properties = properties;
    }

}
