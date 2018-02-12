/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.mp;

/**
 * The <code>RequestStatistics</code> interface defines statistic values about request processing.
 *
 * @author Frank Fock
 * @since 2.4.3
 */
public interface RequestStatistics {

  /**
   * Gets the total number of messages that have been sent on behalf of this request.
   * @return
   *    the number of messages sent (number of retries plus one).
   */
  int getTotalMessagesSent();

  /**
   * Sets the total number of messages that have been sent on behalf of this request.
   * @param totalMessagesSent
   *    the total message count for this request.
   */
  void setTotalMessagesSent(int totalMessagesSent);

  /**
   * Gets the index of the message that has been responded.
   * @return
   *    0 if the initial message has been responded by the command responder.
   *    A value greater than zero indicates, that a retry message has been responded.
   */
  int getIndexOfMessageResponded();

  /**
   * Sets the index of the message that has been responded.
   * @param indexOfMessageResponded
   *    the zero-based index of the message for which the response had been received.
   */
  void setIndexOfMessageResponded(int indexOfMessageResponded);

  /**
   * Gets the time elapsed between the sending of the message and receiving its response.
   * @return
   *    the runtime of the successful request and response message pair in nanoseconds.
   */
  long getResponseRuntimeNanos();

  /**
   * Sets the time elapsed between the sending of the message and receiving its response.
   * @param responseRuntimeNanos
   *    the runtime of the successful request and response message pair in nanoseconds.
   */
  void setResponseRuntimeNanos(long responseRuntimeNanos);

}
