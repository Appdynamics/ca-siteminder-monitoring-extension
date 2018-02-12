/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j;

import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;

/**
 * A <code>CommunityTarget</code> represents SNMP target properties for
 * community based message processing models (SNMPv1 and SNMPv2c).
 * @author Frank Fock
 * @version 1.1
 */
public class CommunityTarget extends AbstractTarget {

  static final long serialVersionUID = 147443821594052003L;

  /**
   * Default constructor.
   */
  public CommunityTarget() {
    setVersion(SnmpConstants.version1);
    setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
    setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv1);
  }

  /**
   * Creates a fully specified community target.
   * @param address
   *    the transport <code>Address</code> of the target.
   * @param community
   *    the community to be used for the target.
   */
  public CommunityTarget(Address address, OctetString community) {
    super(address, community);
    setVersion(SnmpConstants.version1);
    setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
    setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv1);
  }

  /**
   * Gets the community octet string (which is the same as the security name).
   * Thus, you can (and should) use {@link #getSecurityName()} directly.
   * @return
   *    an <code>OctetString</code> instance, should be never <code>null</code> when using this target to send
   *    messages over community based SNMP (v1 and v2c).
   */
  public OctetString getCommunity() {
    return getSecurityName();
  }

  /**
   * Sets the community octet sting. This is a convenience method to set the
   * security name for community based SNMP (v1 and v2c). It basically checks
   * that the community is not <code>null</code> and then calls {@link #setSecurityName(OctetString)}
   * with the supplied parameter.
   * @param community
   *    an <code>OctetString</code> instance which must not be
   *    <code>null</code>.
   */
  public void setCommunity(OctetString community) {
    if (community == null) {
      throw new IllegalArgumentException("Community must not be null");
    }
    setSecurityName(community);
  }

  @Override
  public int getSecurityModel() {
    switch (getVersion()) {
      case SnmpConstants.version1:
        return SecurityModel.SECURITY_MODEL_SNMPv1;
      default:
        return SecurityModel.SECURITY_MODEL_SNMPv2c;
    }
  }

  @Override
  public void setSecurityLevel(int securityLevel) {
    if (securityLevel != SecurityLevel.NOAUTH_NOPRIV) {
      throw new IllegalArgumentException("CommunityTarget only supports SecurityLevel.NOAUTH_NOPRIV");
    }
    super.setSecurityLevel(securityLevel);
  }

  @Override
  public void setSecurityModel(int securityModel) {
    switch (securityModel) {
      case SecurityModel.SECURITY_MODEL_SNMPv1:
        super.setSecurityModel(securityModel);
        super.setVersion(SnmpConstants.version1);
        break;
      case SecurityModel.SECURITY_MODEL_SNMPv2c:
        super.setSecurityModel(securityModel);
        super.setVersion(SnmpConstants.version2c);
        break;
      default:
        throw new UnsupportedOperationException("To set security model for a CommunityTarget, use setVersion");
    }
  }

  public String toString() {
    return "CommunityTarget["+toStringAbstractTarget()+']';
  }


}
