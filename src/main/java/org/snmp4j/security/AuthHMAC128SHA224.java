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
 * The class <code>AuthHMAC128SHA224</code> implements the usmHMAC128SHA224AuthProtocol
 * defined by RFC 7630.
 *
 * @author Frank Fock
 * @since 2.4.0
 */
public class AuthHMAC128SHA224 extends AuthSHA2 {

  public static final OID ID = new OID(new int[] { 1,3,6,1,6,3,10,1,1,4 });
  private static final int DIGEST_LENGTH = 28;
  private static final int AUTH_CODE_LENGTH = 16;

  /**
   * Creates an usmHMAC128SHA224AuthProtocol implementation.
   */
  public AuthHMAC128SHA224() {
    super("SHA-224", ID, DIGEST_LENGTH, AUTH_CODE_LENGTH);
  }

}
