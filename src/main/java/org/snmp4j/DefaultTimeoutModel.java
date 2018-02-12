/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */




package org.snmp4j;

/**
 * The <code>DefaultTimeoutModel</code> implements a timeout model that uses
 * constant timeouts between retries.
 * <p>
 * The total time waited before a request is timed out is therefore:
 * <code>(totalNumberOfRetries + 1) * targetTimeout</code> where each (re)try
 * is timed out after <code>targetTimeout</code> milliseconds.
 *
 * @author Frank Fock
 * @version 1.0
 */
public class DefaultTimeoutModel implements TimeoutModel {

  public DefaultTimeoutModel() {
  }

  public long getRetryTimeout(int retryCount,
                              int totalNumberOfRetries, long targetTimeout) {
    return targetTimeout;
  }

  public long getRequestTimeout(int totalNumberOfRetries, long targetTimeout) {
    return (totalNumberOfRetries+1)*targetTimeout;
  }
}
