/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.security;

/**
 * The <code>SecurityLevel</code> interface contains enumerated values
 * for the different security levels.
 *
 * @author Frank Fock
 * @version 2.0
 */
public enum SecurityLevel {
  undefined(0),
  noAuthNoPriv(1),
  authNoPriv(2),
  authPriv(3);

  /**
   * No authentication and no encryption.
   * Anyone can create and read messages with this security level
   */
  public static final int NOAUTH_NOPRIV = 1;

  /**
   * Authentication and no encryption.
   * Only the one with the right authentication key can create messages
   * with this security level, but anyone can read the contents of
   * the message.
   */
  public static final int AUTH_NOPRIV = 2;

  /**
   * Authentication and encryption.
   * Only the one with the right authentication key can create messages
   * with this security level, and only the one with the right
   * encryption/decryption key can read the contents of the message.
   */
  public static final int AUTH_PRIV = 3;

  private int snmpValue;

  private SecurityLevel(int snmpValue) {
    this.snmpValue = snmpValue;
  }

  /**
   * Gets the SNMP value of this security level.
   * @return
   *    1 for noAuthNoPriv
   *    2 for authNoPriv
   *    3 for authPriv
   */
  public int getSnmpValue() {
    return snmpValue;
  }

  public static SecurityLevel get(int snmpValue) {
    switch (snmpValue) {
      case NOAUTH_NOPRIV: {
        return SecurityLevel.noAuthNoPriv;
      }
      case AUTH_NOPRIV: {
        return SecurityLevel.authNoPriv;
      }
      case AUTH_PRIV: {
        return SecurityLevel.authPriv;
      }
    }
    return SecurityLevel.undefined;
  }
}
