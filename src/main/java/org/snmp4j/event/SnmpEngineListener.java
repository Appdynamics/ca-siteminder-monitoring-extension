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
 * The <code>SnmpEngineListener</code> interface can be implemented by classes
 * that need to be informed about changes to the SNMP engine ID cache.
 *
 * @author Frank Fock
 * @version 1.6
 * @since 1.6
 */
public interface SnmpEngineListener extends EventListener {

  /**
   * An SNMP engine has been added to or removed from the engine cache.
   * @param engineEvent
   *    the SnmpEngineEvent object describing the engine that has been added.
   */
  void engineChanged(SnmpEngineEvent engineEvent);

}
