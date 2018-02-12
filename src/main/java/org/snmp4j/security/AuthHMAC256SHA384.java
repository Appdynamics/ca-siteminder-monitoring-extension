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
 * The class <code>AuthHMAC256SHA384</code> implements the usmHMAC256SHA3846AuthProtocol
 * defined by RFC 7630.
 *
 * @author Frank Fock
 * @since 2.4.0
 */
public class AuthHMAC256SHA384 extends AuthSHA2 {

  public static final OID ID = new OID(new int[] { 1,3,6,1,6,3,10,1,1,6 });
  private static final int DIGEST_LENGTH = 48;
  private static final int AUTH_CODE_LENGTH = 32;

  /**
   * Creates an usmHMAC192SHA256AuthProtocol implementation.
   */
  public AuthHMAC256SHA384() {
    super("SHA-384", ID, DIGEST_LENGTH, AUTH_CODE_LENGTH);
  }

}
