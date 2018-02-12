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
 * The <code>AuthenticationFailureListener</code> listens for authentication
 * failure events.
 *
 * @author Frank Fock
 * @version 1.5
 * @since 1.5
 */
public interface AuthenticationFailureListener extends EventListener {

  /**
   * Informs about an authentication failure occurred while processing the
   * message contained in the supplied event object.
   * @param event
   *    a <code>AuthenticationFailureEvent</code> describing the type of
   *    authentication failure, the offending message, and its source address
   *    and transport protocol.
   */
  void authenticationFailure(AuthenticationFailureEvent event);
}
