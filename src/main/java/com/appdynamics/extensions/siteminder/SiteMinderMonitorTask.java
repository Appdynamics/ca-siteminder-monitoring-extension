/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.appdynamics.extensions.siteminder.metrics.*;
import com.appdynamics.extensions.util.AggregatorFactory;
import com.appdynamics.extensions.util.MetricWriteHelper;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import org.slf4j.LoggerFactory;
import org.snmp4j.Snmp;
import org.snmp4j.Target;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.appdynamics.extensions.siteminder.Util.convertToString;

public class SiteMinderMonitorTask implements Runnable{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SiteMinderMonitorTask.class);
    private static final BigDecimal ERROR_VALUE = BigDecimal.ZERO;
    private static final BigDecimal SUCCESS_VALUE = BigDecimal.ONE;
    private static final String METRICS_COLLECTION_SUCCESSFUL = "Metrics Collection Successful";

    /* metric prefix from the config.yaml to be applied to each metric path*/
    private String metricPrefix;

    /* server properties */
    private Map instance;

    /* a facade to report metrics to the machine agent.*/
    private MetricWriteHelper metricWriter;

    /* a SNMP Factory.*/
    private SNMPFactory snmpFactory;

    /* a utility to collect component metrics. */
    private final ComponentMetricsProcessor componentCollector = new ComponentMetricsProcessor();

    /* a utility to collect cluster metrics. */
    private final ClusterMetricsProcessor clusterMetricsCollector = new ClusterMetricsProcessor();

    private SiteMinderMonitorTask(){
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        String displayName = convertToString(instance.get("displayName"),"");
        MetricPrinter metricPrinter = new MetricPrinter(metricPrefix,displayName,metricWriter);
        try {
            logger.debug("SiteMinder monitor thread for server {} started.",displayName);
            BigDecimal status = extractAndReportMetrics(metricPrinter);
            metricPrinter.printMetric(metricPrinter.formMetricPath(METRICS_COLLECTION_SUCCESSFUL), status
                    , MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL);
        } catch (Exception e) {
            logger.error("Error in SiteMinder Monitor thread for server {}", displayName, e);
            metricPrinter.printMetric(metricPrinter.formMetricPath(METRICS_COLLECTION_SUCCESSFUL), ERROR_VALUE
                    , MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION, MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT, MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL);

        }
        finally{
            long endTime = System.currentTimeMillis() - startTime;
            logger.debug("SiteMinder monitor thread for server {} ended. Time taken = {} and Total metrics reported = {}",displayName,endTime,metricPrinter.getTotalMetricsReported());
        }
    }

    private BigDecimal extractAndReportMetrics(final MetricPrinter metricPrinter) {
        Snmp session = null;
        try {
            session = snmpFactory.createSNMPSession();
            Target target = snmpFactory.createTarget(instance);
            SNMPWalker walker = snmpFactory.createWalker(session,target,instance);
            ResourceIdentityMapper rim = new ResourceIdentityMapper(walker);
            //creates resource map to translate machine ids to names
            Map<String,Map> resourceIdentityMap = rim.mapResources(instance.get("resources"));
            @SuppressWarnings("unchecked")
            List<Map> components = (List<Map>)instance.get("metrics");
            MetricPropertiesBuilder propertiesBuilder = new MetricPropertiesBuilder();
            //a component can be policyServer,webAgents or likes
            for(Map component : components) {
                Map<String, MetricProperties> metricProps = propertiesBuilder.build(component);
                String componentName = component.get("component").toString();
                Map componentIdentityMap = resourceIdentityMap.get(componentName);
                //collects  component level metrics..for eg. for policyServer
                @SuppressWarnings("unchecked")
                List<Metric> componentMetrics = componentCollector.collect(componentName, walker, componentIdentityMap, metricProps);
                //collect all cluster level metrics
                AggregatorFactory aggregatorFactory = new AggregatorFactory();
                clusterMetricsCollector.collect(aggregatorFactory, componentMetrics);
                metricPrinter.reportComponentMetrics(componentMetrics);
                metricPrinter.reportClusterLevelMetrics(aggregatorFactory);

            }
        } catch (IOException e) {
            logger.error("Cannot create a SNMP UDP Session",e);
            return ERROR_VALUE;
        } finally {
            if(session != null){
                try {
                    session.close();
                } catch (IOException e) {
                    logger.error("Cannot close a SNMP UDP Session",e);
                    return ERROR_VALUE;
                }
            }

        }
        return SUCCESS_VALUE;

    }

    static class Builder {
        private SiteMinderMonitorTask task = new SiteMinderMonitorTask();

        Builder metricPrefix(String metricPrefix) {
            task.metricPrefix = metricPrefix;
            return this;
        }

        Builder metricWriter(MetricWriteHelper metricWriter) {
            task.metricWriter = metricWriter;
            return this;
        }

        Builder instance(Map instance){
            task.instance = instance;
            return this;
        }

        Builder snmpFactory(SNMPFactory factory){
            task.snmpFactory = factory;
            return this;
        }

        SiteMinderMonitorTask build() {
            return task;
        }
    }
}

