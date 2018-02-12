/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.util;

/**
 * This models a <code>WorkerTask</code> instance that would be executed by a
 * {@link WorkerPool} upon submission.
 *
 * @author Frank Fock
 * @version 1.9
 * @since 1.9
 */
public interface WorkerTask extends Runnable {

  /**
   * The <code>WorkerPool</code> might call this method to hint the active
   * <code>WorkTask</code> instance to complete execution as soon as possible.
   */
  void terminate();

  /**
   * Waits until this task has been finished.
   */
  void join() throws InterruptedException;

  /**
   * Interrupts this task.
   * @see Thread#interrupt()
   */
  void interrupt();

}
