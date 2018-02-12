/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security.nonstandard;

import org.snmp4j.smi.OID;

/**
 * With the <code>NonStandardSecurityProtocol</code> interface you can modify
 * the ID of a non-standard security protocol to match the ID that is used
 * by that protocol in your environment.
 *
 * @author Frank Fock
 * @since 2.5.0
 */
public interface NonStandardSecurityProtocol {

  /**
   * Assign a new ID to a non-standard security protocol instance.
   *
   * @param newOID
   *    the new security protcol ID for the security protocol class called.
   */
  void setID(OID newOID);

  /**
   * Gets the default ID for this non-standard privacy protocol.
   * @return
   *    the default ID as defined by the OOSNMP-USM-MIB.
   */
  OID getDefaultID();
}
