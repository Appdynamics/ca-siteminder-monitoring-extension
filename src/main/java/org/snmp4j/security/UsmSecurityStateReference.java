/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security;

/**
 * The <code>UsmSecurityStateReference</code> holds cached security data
 * for the {@link USM} security model.
 *
 * @author Frank Fock
 * @version 1.1
 */
public class UsmSecurityStateReference implements SecurityStateReference {

  private byte[] userName;
  private byte[] securityName;
  private byte[] securityEngineID;
  private AuthenticationProtocol authenticationProtocol;
  private PrivacyProtocol privacyProtocol;
  private byte[] authenticationKey;
  private byte[] privacyKey;
  private int securityLevel;

  public UsmSecurityStateReference() {
  }
  public byte[] getUserName() {
    return userName;
  }
  public void setUserName(byte[] userName) {
    this.userName = userName;
  }
  public void setSecurityName(byte[] securityName) {
    this.securityName = securityName;
  }
  public byte[] getSecurityName() {
    return securityName;
  }
  public void setSecurityEngineID(byte[] securityEngineID) {
    this.securityEngineID = securityEngineID;
  }
  public byte[] getSecurityEngineID() {
    return securityEngineID;
  }
  public void setAuthenticationProtocol(AuthenticationProtocol authenticationProtocol) {
    this.authenticationProtocol = authenticationProtocol;
  }
  public AuthenticationProtocol getAuthenticationProtocol() {
    return authenticationProtocol;
  }
  public void setPrivacyProtocol(PrivacyProtocol privacyProtocol) {
    this.privacyProtocol = privacyProtocol;
  }
  public PrivacyProtocol getPrivacyProtocol() {
    return privacyProtocol;
  }
  public void setAuthenticationKey(byte[] authenticationKey) {
    this.authenticationKey = authenticationKey;
  }
  public byte[] getAuthenticationKey() {
    return authenticationKey;
  }
  public void setPrivacyKey(byte[] privacyKey) {
    this.privacyKey = privacyKey;
  }
  public byte[] getPrivacyKey() {
    return privacyKey;
  }
  public void setSecurityLevel(int securityLevel) {
    this.securityLevel = securityLevel;
  }
  public int getSecurityLevel() {
    return securityLevel;
  }
}
