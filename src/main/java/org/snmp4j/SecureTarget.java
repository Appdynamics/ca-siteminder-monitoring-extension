/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j;

import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;

import java.io.Serializable;

/**
 * The <code>SecureTarget</code> is an security model independent abstract class
 * for all targets supporting secure SNMP communication.
 *
 * @author Jochen Katz & Frank Fock
 * @version 2.0
 */
public abstract class SecureTarget
    extends AbstractTarget implements Serializable {

  private static final long serialVersionUID = 3864834593299255038L;

  /**
   * Default constructor.
   */
  protected SecureTarget() {
  }

  /**
   * Creates a SNMPv3 secure target with an address and security name.
   * @param address
   *    an <code>Address</code> instance denoting the transport address of the
   *    target.
   * @param securityName
   *    a <code>OctetString</code> instance representing the security name
   *    of the USM user used to access the target.
   */
  protected SecureTarget(Address address, OctetString securityName) {
    super(address, securityName);
  }

  @Override
  public String toString() {
    return "SecureTarget[" + toStringAbstractTarget() + ']';
  }
}
