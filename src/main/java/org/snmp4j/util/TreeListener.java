/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.util;

import java.util.EventListener;

/**
 * The <code>TreeListener</code> interface is implemented by objects
 * listening for tree events.
 *
 * @author Frank Fock
 * @version 1.10
 * @since 1.8
 * @see TreeUtils
 */
public interface TreeListener extends EventListener {

  /**
   * Consumes the next table event, which is typically the next row in a
   * table retrieval operation.
   *
   * @param event
   *    a <code>TableEvent</code> instance.
   * @return
   *    <code>true</code> if this listener wants to receive more events,
   *    otherwise return <code>false</code>. For example, a
   *    <code>TreeListener</code> can return <code>false</code> to stop
   *    tree retrieval.
   */
  boolean next(TreeEvent event);

  /**
   * Indicates in a series of tree events that no more events will follow.
   * @param event
   *    a <code>TreeEvent</code> instance that will either indicate an error
   *    ({@link TreeEvent#isError()} returns <code>true</code>) or success
   *    of the tree retrieval operation.
   */
  void finished(TreeEvent event);

  /**
   * Indicates whether the tree walk is complete or not.
   * @return
   *    <code>true</code> if it is complete, <code>false</code> otherwise.
   * @since 1.10
   */
  boolean isFinished();
}
