/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security;

import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.nonstandard.NonStandardSecurityProtocol;
import org.snmp4j.smi.OID;

/**
 * Encryption class for AES 192.
 *
 * @author Jochen Katz
 * @version 1.11
 */
public class PrivAES192 extends PrivAES implements NonStandardSecurityProtocol {

  private static final long serialVersionUID = -3496699866363408441L;

  /**
   * Unique ID of this privacy protocol.
   */
  public static OID ID = new OID(SnmpConstants.oosnmpUsmAesCfb192Protocol);

  private OID oid;

  /**
   * Constructor.
   */
  public PrivAES192() {
    super(24);
  }

  /**
   * Gets the OID uniquely identifying the privacy protocol.
   * @return
   *    an <code>OID</code> instance.
   */
  public OID getID() {
        return (oid == null) ? getDefaultID() : oid;
  }

  @Override
  public void setID(OID newID) {
    oid = new OID(newID);
  }

  @Override
  public OID getDefaultID() {
    return (OID)ID.clone();
  }
}
