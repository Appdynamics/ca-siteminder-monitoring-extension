/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */




package org.snmp4j;

/**
 * The <code>TimeoutModel</code> is the common interface for all models
 * of timing out a SNMP request. The default model is a linear model, thus
 * each retry has the same delay as specified by the {@link Target#getTimeout()}
 * value.
 *
 * @author Frank Fock
 * @version 1.0
 */

public interface TimeoutModel {

  /**
   * Gets the timeout for the specified retry (a zero value for
   * <code>retryCount</code> specifies the first request).
   * @param retryCount
   *    the number of retries already performed for the target.
   * @param totalNumberOfRetries
   *    the total number of retries configured for the target.
   * @param targetTimeout
   *    the timeout as specified for the target in milliseconds.
   * @return long
   *    the timeout duration in milliseconds for the supplied retry.
   */
  public long getRetryTimeout(int retryCount,
                              int totalNumberOfRetries, long targetTimeout);

  /**
   * Gets the timeout for all retries, which is defined as the sum of
   * {@link #getRetryTimeout(int retryCount, int totalNumberOfRetries,
   * long targetTimeout)}
   * for all <code>retryCount</code> in
   * <code>0 <= retryCount < totalNumberOfRetries</code>.
   *
   * @param totalNumberOfRetries
   *    the total number of retries configured for the target.
   * @param targetTimeout
   *    the timeout as specified for the target in milliseconds.
   * @return
   *    the time in milliseconds when the request will be timed out finally.
   */
  public long getRequestTimeout(int totalNumberOfRetries, long targetTimeout);
}
