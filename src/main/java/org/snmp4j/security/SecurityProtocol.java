/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security;

import org.snmp4j.smi.OID;

import java.io.Serializable;

/**
 * The <code>SecurityProtocol</code> class defines common methods of
 * authentication and privacy protocols.
 *
 * @author Jochen Katz & Frank Fock
 * @version 2.5.0
 */
public interface SecurityProtocol extends Serializable {

  /**
   * Gets the OID uniquely identifying the privacy protocol.
   * @return
   *    an <code>OID</code> instance.
   */
  OID getID();

  /**
   * Checks whether this security protocol is actually supported by this Java runtime environment.
   * @return
   *    <code>true</code> if this security protocol is supported, <code>false</code> otherwise.
   * @since 2.5.0
   */
  boolean isSupported();

}

