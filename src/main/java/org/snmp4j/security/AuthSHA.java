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
 * The <code>SHA</code> class implements the Secure Hash Authentication.
 *
 * @author Jochen Katz
 * @version 1.0
 */
public class AuthSHA
    extends AuthGeneric {

  private static final long serialVersionUID = 2355896418236397919L;

  public static final OID ID = new OID(SnmpConstants.usmHMACSHAAuthProtocol);

  public AuthSHA() {
    super("SHA-1", 20);
  }

  public OID getID() {
    return (OID) ID.clone();
  }

}
