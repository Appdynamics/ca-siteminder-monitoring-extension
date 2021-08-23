/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.appdynamics.extensions.ABaseMonitor;
import com.appdynamics.extensions.TasksExecutionServiceProvider;
import com.appdynamics.extensions.conf.MonitorContextConfiguration;
import com.appdynamics.extensions.logging.ExtensionsLoggerFactory;
import com.appdynamics.extensions.util.AssertUtils;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;


/**
 * Monitors CA's siteminder product.
 */

public class SiteMinderMonitor extends ABaseMonitor {

    private static final Logger logger = ExtensionsLoggerFactory.getLogger(SiteMinderMonitor.class);
    private static final String METRIC_PREFIX = "Custom Metrics|SiteMinder|";
    private static final String MONITOR_NAME = "Siteminder monitor";
    private long previousTimestamp = 0;
    private long currentTimestamp = System.currentTimeMillis();

    @Override
    protected String getDefaultMetricPrefix() {
        return METRIC_PREFIX;
    }

    @Override
    public String getMonitorName() {
        return MONITOR_NAME;
    }

    @Override
    protected void doRun(TasksExecutionServiceProvider tasksExecutionServiceProvider) {
        List<Map<String, ?>> instances = (List<Map<String, ?>>) getContextConfiguration().getConfigYml().get("instances");
        AssertUtils.assertNotNull(instances, "Instances cannot be null or empty in config");
        logger.debug("Started Extension");
        previousTimestamp = currentTimestamp;
        currentTimestamp = System.currentTimeMillis();
        if (previousTimestamp != 0) {
            for (Map<String, ?> instance : instances) {
                try {
                    SiteMinderMonitorTask task = createTask(instance, tasksExecutionServiceProvider);
                    tasksExecutionServiceProvider.submit((String) instance.get("displayName"), task);
                } catch (Exception e) {
                    logger.error("Error while creating task for {}", Util.convertToString(instance.get("displayName"), ""),e);
                }
            }
        }

    }

    private SiteMinderMonitorTask createTask(Map instance,TasksExecutionServiceProvider tasksExecutionServiceProvider) {
        return new SiteMinderMonitorTask.Builder()
                .instance(instance)
                .metricPrefix(getContextConfiguration().getMetricPrefix())
                .metricWriter(tasksExecutionServiceProvider.getMetricWriteHelper())
                .snmpFactory(new SNMPFactory())
                .build();
    }


    @Override
    protected List<Map<String, ?>> getServers() {
        return (List<Map<String, ?>>) getContextConfiguration().getConfigYml().get("instances");
    }
}
