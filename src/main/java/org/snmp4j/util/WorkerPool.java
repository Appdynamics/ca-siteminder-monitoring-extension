/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.util;

/**
 * The <code>WorkerPool</code> interface models an abstract pool of workers
 * (threads) which can execute {@link WorkerTask}s concurrently.
 *
 * @author Frank Fock
 * @version 1.9
 */
public interface WorkerPool {

  /**
   * Executes a task on behalf of this worker pool. If all threads are currently
   * busy, this method call blocks until a worker gets idle again which is when
   * the call returns immediately.
   * @param task
   *    a <code>Runnable</code> to execute.
   */
  void execute(WorkerTask task);

  /**
   * Tries to execute a task on behalf of this worker pool. If all threads are
   * currently busy, this method returns <code>false</code>. Otherwise the task
   * is executed in background.
   * @param task
   *    a <code>Runnable</code> to execute.
   * @return
   *    <code>true</code> if the task is executing.
   */
  boolean tryToExecute(WorkerTask task);

  /**
   * Stops all threads in this worker pool gracefully. This method will not
   * return until all threads have been terminated and joined successfully.
   */
  void stop();

  /**
   * Cancels all threads non-blocking by interrupting them.
   */
  void cancel();

  /**
   * Checks if all workers of the pool are idle.
   * @return
   *    <code>true</code> if all workers are idle.
   */
  boolean isIdle();

}
