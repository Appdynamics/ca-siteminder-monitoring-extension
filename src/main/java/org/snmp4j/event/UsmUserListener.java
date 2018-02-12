/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */




package org.snmp4j.event;

import java.util.EventListener;

/**
 * The <code>UsmUserListener</code> interface is implemented by objects that
 * need to be informed when a USM user is created, modified, or deleted.
 *
 * @author Frank Fock
 * @version 1.0
 */
public interface UsmUserListener extends EventListener {

  /**
   * Indicates a USM user change.
   * @param event
   *    an <code>UsmUserEvent</code>.
   */
  void usmUserChange(UsmUserEvent event);

}
