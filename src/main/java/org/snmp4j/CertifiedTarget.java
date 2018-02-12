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
import org.snmp4j.smi.TlsAddress;

import java.io.Serializable;

/**
 * The <code>CertifiedTarget</code> class implements a {@link SecureTarget}
 * for usage with {@link org.snmp4j.security.SecurityModel}s that support
 * secured connections using client and server certificates.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public class CertifiedTarget extends SecureTarget implements CertifiedIdentity, Serializable {

  private static final long serialVersionUID = 1L;

  private OctetString serverFingerprint;
  private OctetString clientFingerprint;

  public CertifiedTarget(OctetString identity) {
    super(new TlsAddress(), identity);
  }

  public CertifiedTarget(Address address, OctetString identity,
                         OctetString serverFingerprint, OctetString clientFingerprint) {
    super(address, identity);
    this.serverFingerprint = serverFingerprint;
    this.clientFingerprint = clientFingerprint;
  }

  public OctetString getServerFingerprint() {
    return serverFingerprint;
  }

  public OctetString getClientFingerprint() {
    return clientFingerprint;
  }

  public OctetString getIdentity() {
    return super.getSecurityName();
  }

  @Override
  public String toString() {
    return "CertifiedTarget[" + toStringAbstractTarget()+
        ", serverFingerprint=" + serverFingerprint +
        ", clientFingerprint=" + clientFingerprint +
        ']';
  }
}
