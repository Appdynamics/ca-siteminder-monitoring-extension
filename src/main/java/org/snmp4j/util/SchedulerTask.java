/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.util;

/**
 * The <code>SchedulerTask</code> extends the <code>Runnable</code> interface
 * by methods that are needed for recurrent execution of a task.
 *
 * @author Frank Fock
 * @version 1.9
 * @since 1.6
 */
public interface SchedulerTask extends WorkerTask {

  /**
   * Checks whether this task is ready to be executed. A task is
   * @return
   *    <code>true</code> if this task can be executed now.
   */
  boolean isReadyToRun();

  /**
   * Returns <code>true</code> if this task is finished and should never be
   * executed again.
   * @return
   *    <code>true</code> if this task is finished and cannot be executed
   *    anymore.
   */
  boolean isDone();

}
