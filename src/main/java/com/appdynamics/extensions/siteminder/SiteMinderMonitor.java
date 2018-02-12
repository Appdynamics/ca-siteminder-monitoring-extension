/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.appdynamics.extensions.conf.MonitorConfiguration;
import com.appdynamics.extensions.util.MetricWriteHelper;
import com.appdynamics.extensions.util.MetricWriteHelperFactory;
import com.google.common.collect.Maps;
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Monitors CA's siteminder product.
 */

public class SiteMinderMonitor extends AManagedMonitor{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SiteMinderMonitor.class);
    private static final String CONFIG_ARG = "config-file";
    private static final String METRIC_PREFIX = "Custom Metrics|SiteMinder|";

    private boolean initialized;
    private MonitorConfiguration configuration;

    public SiteMinderMonitor() {
        System.out.println(logVersion());
    }

    public TaskOutput execute(Map<String, String> taskArgs, TaskExecutionContext out) throws TaskExecutionException {
        logVersion();
        if(!initialized){
            initialize(taskArgs);
        }
        logger.debug("The raw arguments are {}", taskArgs);
        configuration.executeTask();
        logger.info("SiteMinder monitor run completed successfully.");
        return new TaskOutput("SiteMinder monitor run completed successfully.");
    }

    private void initialize(Map<String, String> taskArgs) {
        if(!initialized){
            //read the config.
            final String configFilePath = taskArgs.get(CONFIG_ARG);
            MetricWriteHelper metricWriteHelper = MetricWriteHelperFactory.create(this);
            MonitorConfiguration conf = new MonitorConfiguration(METRIC_PREFIX, new TaskRunnable(), metricWriteHelper);
            conf.setConfigYml(configFilePath);
            conf.checkIfInitialized(MonitorConfiguration.ConfItem.CONFIG_YML, MonitorConfiguration.ConfItem.EXECUTOR_SERVICE,
                    MonitorConfiguration.ConfItem.METRIC_PREFIX,MonitorConfiguration.ConfItem.METRIC_WRITE_HELPER);
            this.configuration = conf;
            initialized = true;
        }
    }

    private class TaskRunnable implements Runnable{

        public void run() {
            Map<String, ?> config = configuration.getConfigYml();
            if (config != null) {
                List<Map> instances = (List<Map>) config.get("instances");
                if (instances != null && !instances.isEmpty()) {
                    for (Map instance : instances) {
                        SiteMinderMonitorTask task = createTask(instance);
                        configuration.getExecutorService().execute(task);
                    }
                }
                else {
                    logger.error("There are no instances configured");
                }
            } else {
                logger.error("The config.yml is not loaded due to previous errors.The task will not run");
            }
        }
    }

    private SiteMinderMonitorTask createTask(Map instance) {
        return new SiteMinderMonitorTask.Builder()
                .instance(instance)
                .metricPrefix(configuration.getMetricPrefix())
                .metricWriter(configuration.getMetricWriter())
                .snmpFactory(new SNMPFactory())
                .build();
    }

    /*private String getPassword(Map server) {
        String password = convertStr(server.get(PASSWORD));
        if(!Strings.isNullOrEmpty(password)){
            return password;
        }
        String encryptionKey = convertStr(configuration.getConfigYml().get(ConfigUtils.ENCRYPTION_KEY));
        String encryptedPassword = convertStr(server.get(ENCRYPTED_PASSWORD));
        if(!Strings.isNullOrEmpty(encryptionKey) && !Strings.isNullOrEmpty(encryptedPassword)){
            java.util.Map<String,String> cryptoMap = Maps.newHashMap();
            cryptoMap.put(PASSWORD_ENCRYPTED,encryptedPassword);
            cryptoMap.put(TaskInputArgs.ENCRYPTION_KEY,encryptionKey);
            return CryptoUtil.getPassword(cryptoMap);
        }
        return null;
    }*/

    private static String getImplementationVersion() {
        return SiteMinderMonitor.class.getPackage().getImplementationTitle();
    }

    private String logVersion() {
        String msg = "Using Monitor Version [" + getImplementationVersion() + "]";
        logger.info(msg);
        return msg;
    }


    public static void main(String[] args) throws TaskExecutionException {
        SiteMinderMonitor monitor = new SiteMinderMonitor();
        Map<String, String> taskArgs = Maps.newHashMap();
        taskArgs.put(CONFIG_ARG, "src/test/resources/conf/config.yml");
        monitor.execute(taskArgs, null);
    }
}
