/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security;

import org.snmp4j.smi.OID;

/**
 * The <code>SHA-2</code> class implements the Secure Hash Authentication 2.
 *
 * @author Frank Fock
 * @version 2.4.0
 */
public class AuthSHA2 extends AuthGeneric {

  private static final long serialVersionUID = 1L;

  /** The object identifier that identifies this authentication protocol.*/
  private OID protocolID;

  /**
   * Creates a SHA authentication protocol with the specified digest length.
   * @param protocolName
   *   the SHA protocol name (i.e., "SHA-256").
   * @param protocolOID
   *   the OID of the protocol as defined in RFC 7630.
   * @param digestLength
   *   the digest length.
   * @param authenticationCodeLength
   *   the length of the authentication hash output in octets.
   */
  public AuthSHA2(String protocolName, OID protocolOID, int digestLength, int authenticationCodeLength) {
    super(protocolName, digestLength, authenticationCodeLength);
    this.protocolID = protocolOID;
  }

  public OID getID() {
    return (OID) protocolID.clone();
  }


}
