/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.mp;

/**
 * The <code>MessageID</code> interface defines the characteristics of a SNMP message ID
 * as defined by RFC 3412 §6.2.
 *
 * @author Frank Fock
 * @since 2.4.3
 */
public interface MessageID {

  /**
   * Gets the integer representation of the message ID.
   * @return
   *    the message ID as a value between 0 and 2147483647 (inclusive).
   */
  int getID();

}
