/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.appdynamics.extensions.ABaseMonitor;
import com.appdynamics.extensions.MetricWriteHelperFactory;
import com.appdynamics.extensions.TasksExecutionServiceProvider;
import com.appdynamics.extensions.conf.MonitorContextConfiguration;
import com.appdynamics.extensions.logging.ExtensionsLoggerFactory;
import com.appdynamics.extensions.MetricWriteHelper;
import com.appdynamics.extensions.siteminder.instances.InstancesInfo;
import com.appdynamics.extensions.util.AssertUtils;
import com.appdynamics.extensions.util.CryptoUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;


/**
 * Monitors CA's siteminder product.
 */

public class SiteMinderMonitor extends ABaseMonitor {

    private static final Logger logger = ExtensionsLoggerFactory.getLogger(SiteMinderMonitor.class);
    private static final String CONFIG_ARG = "config-file";
    private static final String METRIC_PREFIX = "Custom Metrics|SiteMinder|";
    private long previousTimestamp = 0;
    private long currentTimestamp = System.currentTimeMillis();

    private boolean initialized;
    private MonitorContextConfiguration configuration;

    @Override
    protected String getDefaultMetricPrefix() {
        return Constant.METRIC_PREFIX;
    }

    @Override
    public String getMonitorName() {
        return "Siteminder monitor";
    }

    @Override
    protected void doRun(TasksExecutionServiceProvider tasksExecutionServiceProvider) {
        List<Map<String, ?>> servers = (List<Map<String, ?>>) getContextConfiguration().getConfigYml().get("servers");
        logger.debug("Started Extension");
        previousTimestamp = currentTimestamp;
        currentTimestamp = System.currentTimeMillis();
        if (previousTimestamp != 0) {
            for (Map<String, ?> server : servers) {
                try {
                    SiteMinderMonitorTask task = createTask(server, tasksExecutionServiceProvider);
                    tasksExecutionServiceProvider.submit((String) server.get("displayName"), task);
                } catch (Exception e) {
                    logger.error("Error while creating task for {}", Util.convertToString(server.get("displayName"), ""),e);
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
        return (List<Map<String, ?>>) getContextConfiguration().getConfigYml().get("servers");
    }


//    public static void main(String[] args) throws TaskExecutionException {
//        ConsoleAppender ca = new ConsoleAppender();
//        ca.setWriter(new OutputStreamWriter(System.out));
//        ca.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
//        ca.setThreshold(Level.DEBUG);
//        org.apache.log4j.Logger.getRootLogger().addAppender(ca);
//        SiteMinderMonitor monitor = new SiteMinderMonitor();
//        Map<String, String> taskArgs = Maps.newHashMap();
//        taskArgs.put(CONFIG_ARG, "src/main/resources/conf/config.yml");
//        monitor.execute(taskArgs, null);
//    }
}
