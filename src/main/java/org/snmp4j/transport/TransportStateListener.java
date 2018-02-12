/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.transport;

import java.util.EventListener;

/**
 * The <code>TransportStateListener</code> interface can be implemented
 * to monitor the connection state for connection oriented transport mappings.
 *
 * @author Frank Fock
 * @version 1.7
 * @since 1.7
 */
public interface TransportStateListener extends EventListener {

  /**
   * The connection state of a transport protocol connection has been changed.
   * @param change
   *    a <code>TransportStateEvent</code> instance.
   */
  void connectionStateChanged(TransportStateEvent change);
}
