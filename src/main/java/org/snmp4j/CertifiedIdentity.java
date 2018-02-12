/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j;

import org.snmp4j.smi.OctetString;

/**
 * The <code>CertifiedIdentity</code>  interface describes an identity
 * that is associated with a client certificate fingerprint and a server
 * certificate fingerprint.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public interface CertifiedIdentity {

  OctetString getServerFingerprint();

  OctetString getClientFingerprint();

  OctetString getIdentity();

}
