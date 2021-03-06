/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j;

import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;

// for JavaDoc

/**
 * User based target for SNMPv3 or later.
 *
 * @author Frank Fock
 * @version 2.0
 */
public class UserTarget extends SecureTarget {

  private static final long serialVersionUID = -1426511355567423746L;

  private OctetString authoritativeEngineID = new OctetString();

  /**
   * Creates a target for a user based security model target.
   */
  public UserTarget() {
    setSecurityModel(MPv3.ID);
  }

  /**
   * Creates a SNMPv3 USM target with security level noAuthNoPriv, one second
   * time-out without retries.
   * @param address
   *    the transport <code>Address</code> of the target.
   * @param securityName
   *    the USM security name to be used to access the target.
   * @param authoritativeEngineID
   *    the authoritative engine ID as a possibly zero length byte
   *    array which must not be <code>null</code>.
   */
  public UserTarget(Address address, OctetString securityName,
                    byte[] authoritativeEngineID) {
    super(address, securityName);
    setAuthoritativeEngineID(authoritativeEngineID);
    setSecurityModel(MPv3.ID);
  }

  /**
   * Creates a SNMPv3 USM target with the supplied security level, one second
   * time-out without retries.
   * @param address
   *    the transport <code>Address</code> of the target.
   * @param securityName
   *    the USM security name to be used to access the target.
   * @param authoritativeEngineID
   *    the authoritative engine ID as a possibly zero length byte
   *    array which must not be <code>null</code>.
   * @param securityLevel
   *    the {@link SecurityLevel} to use.
   * @since 1.1
   */
  public UserTarget(Address address, OctetString securityName,
                    byte[] authoritativeEngineID, int securityLevel) {
    super(address, securityName);
    setAuthoritativeEngineID(authoritativeEngineID);
    setSecurityLevel(securityLevel);
    setSecurityModel(MPv3.ID);
  }

  /**
   * Sets the authoritative engine ID of this target.
   * @param authoritativeEngineID
   *    a possibly zero length byte array (must not be <code>null</code>).
   */
  public void setAuthoritativeEngineID(byte[] authoritativeEngineID) {
    this.authoritativeEngineID.setValue(authoritativeEngineID);
  }

  /**
   * Gets the authoritative engine ID of this target.
   * @return
   *    a possibly zero length byte array.
   */
  public byte[] getAuthoritativeEngineID() {
    return authoritativeEngineID.getValue();
  }

  @Override
  public String toString() {
    return "UserTarget[" + toStringAbstractTarget() +
        ", authoritativeEngineID=" + authoritativeEngineID +
        ']';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    UserTarget that = (UserTarget) o;

    if (authoritativeEngineID != null ? !authoritativeEngineID.equals(that.authoritativeEngineID) : that.authoritativeEngineID != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (authoritativeEngineID != null ? authoritativeEngineID.hashCode() : 0);
    return result;
  }
}

