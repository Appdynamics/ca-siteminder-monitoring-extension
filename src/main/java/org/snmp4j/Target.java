/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j;

import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;

import java.io.Serializable;
import java.util.List;

// for JavaDoc

/**
 * A <code>Target</code> interface defines an abstract representation of a
 * remote SNMP entity. It represents a target with an Address object, as well
 * protocol parameters such as retransmission and timeout policy.
 *
 * @author Frank Fock
 * @version 2.0
 */
public interface Target extends Serializable, Cloneable {

  /**
   * Gets the address of this target.
   * @return
   *    an Address instance.
   */
  Address getAddress();

  /**
   * Sets the address of the target.
   * @param address
   *    an Address instance.
   */
  void setAddress(Address address);

  /**
   * Sets the SNMP version (thus the SNMP messagen processing model) of the
   * target.
   * @param version
   *    the message processing model ID.
   * @see SnmpConstants#version1
   * @see SnmpConstants#version2c
   * @see SnmpConstants#version3
   */
  void setVersion(int version);

  /**
   * Gets the SNMP version (NMP messagen processing model) of the target.
   * @return
   *    the message processing model ID.
   * @see SnmpConstants#version1
   * @see SnmpConstants#version2c
   * @see SnmpConstants#version3
   */
  int getVersion();

  /**
   * Sets the number of retries to be performed before a request is timed out.
   * @param retries
   *    the number of retries. <em>Note: If the number of retries is set to
   *    0, then the request will be sent out exactly once.</em>
   */
  void setRetries(int retries);

  /**
   * Gets the number of retries.
   * @return
   *    an integer >= 0.
   */
  int getRetries();

  /**
   * Sets the timeout for a target.
   * @param timeout
   *    timeout in milliseconds before a confirmed request is resent or
   *    timed out.
   */
  void setTimeout(long timeout);

  /**
   * Gets the timeout for a target.
   * @return
   *    the timeout in milliseconds.
   */
  long getTimeout();

  /**
   * Gets the maximum size of request PDUs that this target is able to respond
   * to. The default is 65535.
   * @return
   *    the maximum PDU size of request PDUs for this target. Which is always
   *    greater than 484.
   */
  int getMaxSizeRequestPDU();

  /**
   * Sets the maximum size of request PDUs that this target is able to receive.
   * @param maxSizeRequestPDU
   *    the maximum PDU (SNMP message) size this session will be able to
   *    process.
   */
  void setMaxSizeRequestPDU(int maxSizeRequestPDU);

  /**
   * Gets the prioritised list of transport mappings to be used for this
   * target. The first mapping in the list that matches the target address
   * is chosen for sending new requests.
   *
   * @return
   *    an ordered list of {@link TransportMapping} instances.
   * @since 2.0
   */
  List<TransportMapping<? extends Address>> getPreferredTransports();

  Object clone();

  /**
   * Gets the security model associated with this target.
   * @return
   *    an <code>int</code> value as defined in the {@link org.snmp4j.security.SecurityModel}
   *    interface or any third party subclass thereof.
   */
  int getSecurityModel();

  /**
   * Gets the security name associated with this target. The security name
   * is used by the security model to lookup further parameters like
   * authentication and privacy protocol settings from the security model
   * dependent internal storage.
   * @return
   *   an <code>OctetString</code> instance (never <code>null</code>).
   */
  OctetString getSecurityName();

  /**
   * Gets the security level associated with this target.
   * @return
   *   one of
   *   <P><UL>
   *   <LI>{@link org.snmp4j.security.SecurityLevel#NOAUTH_NOPRIV}
   *   <LI>{@link org.snmp4j.security.SecurityLevel#AUTH_NOPRIV}
   *   <LI>{@link org.snmp4j.security.SecurityLevel#AUTH_PRIV}
   *   </UL></P>
   */
  int getSecurityLevel();

  /**
   * Sets the security level for this target. The supplied security level must
   * be supported by the security model dependent information associated with
   * the security name set for this target.
   * @param securityLevel
   *   one of
   *   <P><UL>
   *   <LI>{@link org.snmp4j.security.SecurityLevel#NOAUTH_NOPRIV}
   *   <LI>{@link org.snmp4j.security.SecurityLevel#AUTH_NOPRIV}
   *   <LI>{@link org.snmp4j.security.SecurityLevel#AUTH_PRIV}
   *   </UL></P>
   */
  void setSecurityLevel(int securityLevel);

  /**
   * Sets the security model for this target.
   * @param securityModel
   *    an <code>int</code> value as defined in the {@link org.snmp4j.security.SecurityModel}
   *    interface or any third party subclass thereof.
   */
  void setSecurityModel(int securityModel);

  /**
   * Sets the security name to be used with this target.
   * @param securityName
   *    an <code>OctetString</code> instance (must not be <code>null</code>).
   * @see #getSecurityName()
   */
  void setSecurityName(OctetString securityName);
}

