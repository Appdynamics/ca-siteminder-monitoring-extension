/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.transport.tls;

import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;

import java.security.cert.Certificate;

/**
 * The <code>TlsTmSecurityCallback</code> is implemented by the
 * SnmpTlsMib (of SNMP4J-Agent), for example, to resolve (lookup) the
 * <code>tmSecurityName</code> for incoming requests.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public interface TlsTmSecurityCallback<C extends Certificate> {

  /**
   * Gets the tmSecurityName (see RFC 5953) from the certificate chain
   * of the communication peer that needs to be authenticated.
   *
   * @param peerCertificateChain
   *    an array of {@link Certificate}s with the peer's own certificate
   *    first followed by any CA authorities.
   * @return
   *    the tmSecurityName as defined by RFC 5953.
   */
  OctetString getSecurityName(C[] peerCertificateChain);

  /**
   * Check if the supplied peer end certificate is accepted as client.
   * @param peerEndCertificate
   *    a client Certificate instance to check acceptance for.
   * @return
   *    <tt>true</tt> if the certificate is accepted.
   */
  boolean isClientCertificateAccepted(C peerEndCertificate);

  /**
   * Check if the supplied peer certificate chain is accepted as server.
   * @param peerCertificateChain
   *    a server Certificate chain to check acceptance for.
   * @return
   *    <tt>true</tt> if the certificate chain is accepted.
   */
  boolean isServerCertificateAccepted(C[] peerCertificateChain);

  /**
   * Check if the supplied issuer certificate is accepted as server.
   * @param issuerCertificate
   *    an issuer Certificate instance to check acceptance for.
   * @return
   *    <tt>true</tt> if the certificate is accepted.
   */
  boolean isAcceptedIssuer(C issuerCertificate);

  /**
   * Gets the local certificate alias to be used for the supplied
   * target address.
   * @param targetAddress
   *    a target address or <tt>null</tt> if the default local
   *    certificate alias needs to be retrieved.
   * @return
   *    the requested local certificate alias, if known.
   *    Otherwise <tt>null</tt> is returned which could cause
   *    a protocol violation if the local key store contains more
   *    than one certificate.
   */
  String getLocalCertificateAlias(Address targetAddress);

}
