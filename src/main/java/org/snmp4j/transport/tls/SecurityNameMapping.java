/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.transport.tls;

import org.snmp4j.smi.OctetString;

/**
 * The <tt>SecurityNameMapping</tt> maps a X509 certificate identified by its
 * fingerprint to a security name based on a mapping defined by
 * {@link CertMappingType}.
 *
 * @author Frank Fock
 * @since 2.0
 */
public class SecurityNameMapping {

  public enum CertMappingType
  { Specified, SANRFC822Name, SANDNSName, SANIpAddress, SANAny, CommonName }

  private OctetString fingerprint;
  private OctetString data;
  private CertMappingType type;
  private OctetString securityName;

  public SecurityNameMapping(OctetString fingerprint, OctetString data, CertMappingType type,
                             OctetString securityName) {
    this.fingerprint = fingerprint;
    this.data = data;
    this.type = type;
    this.securityName = securityName;
  }

  public OctetString getFingerprint() {
    return fingerprint;
  }

  public OctetString getData() {
    return data;
  }

  public CertMappingType getType() {
    return type;
  }

  public OctetString getSecurityName() {
    return securityName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityNameMapping that = (SecurityNameMapping) o;

    if (data != null ? !data.equals(that.data) : that.data != null) return false;
    if (fingerprint != null ? !fingerprint.equals(that.fingerprint) : that.fingerprint != null) return false;
    if (type != that.type) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = fingerprint != null ? fingerprint.hashCode() : 0;
    result = 31 * result + (data != null ? data.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SecurityNameMapping{" +
        "fingerprint=" + fingerprint +
        ", data=" + data +
        ", type=" + type +
        ", securityName=" + securityName +
        '}';
  }
}
