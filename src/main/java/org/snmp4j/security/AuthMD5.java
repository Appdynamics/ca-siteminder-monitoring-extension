/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security;

import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;

/**
 * The AuthMD5 class implements the MD5 authentication protocol.
 *
 * @author Jochen Katz & Frank Fock
 * @version 1.0
 */
public class AuthMD5
    extends AuthGeneric {

  private static final long serialVersionUID = -5972274836195217352L;

  public static final OID ID = new OID(SnmpConstants.usmHMACMD5AuthProtocol);

  public AuthMD5() {
    super("MD5", 16);
  }

  public OID getID() {
    return (OID) ID.clone();
  }
}
