/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.util;

/**
 * The <code>ThreadFactory</code> describes a factory for threads of execution
 * modeled as <code>WorkerTask</code>s.
 *
 * @author Frank Fock
 * @version 1.9
 * @since 1.9
 */
public interface ThreadFactory {

  /**
   * Creates a new thread of execution for the supplied task. The returned
   * <code>WorkerTask</code> is a symmetric wrapper for the supplied one.
   * When the returned task is being run, the supplied one will be executed
   * in a new thread of execution until it either terminates or the
   * {@link WorkerTask#terminate()} method has been called.
   *
   * @param name
   *    the name of the execution thread.
   * @param task
   *    the task to be executed in the new thread.
   * @param daemon
   *    indicates whether the new thread is a daemon (<code>true</code> or an
   *    user thread (<code>false</code>).
   * @return
   *    the <code>WorkerTask</code> wrapper to control start and termination of
   *    the thread.
   */
  WorkerTask createWorkerThread(String name, WorkerTask task, boolean daemon);

}
