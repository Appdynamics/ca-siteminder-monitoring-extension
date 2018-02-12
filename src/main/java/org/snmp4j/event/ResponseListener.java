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
 * The <code>ResponseListener</code> interface is implemented by objects that
 * process SNMP response messages.
 *
 * @author Frank Fock
 * @version 1.0
 */
public interface ResponseListener extends EventListener {

  /**
   * Process a SNMP response.
   * @param event
   *   a <code>ResponseEvent</code>.
   */
  void onResponse(ResponseEvent event);

}
