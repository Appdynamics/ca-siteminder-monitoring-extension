/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security.nonstandard;

import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;

/**
 * This class is provided for interoperability with some broken AES 192bit implementations of major
 * network device manufactures which use a key extension algorithm that was specified for
 * {@link org.snmp4j.security.Priv3DES} but was never specified for AES 192 and 256 bit.
 *
 * Note: DO NOT USE THIS CLASS IF YOU WANT TO COMPLY WITH draft-blumenthal-aes-usm-04.txt!
 *
 * @author Frank Fock
 * @version 2.2.3
 * @since 2.2.3
 */
public class PrivAES192With3DESKeyExtension extends PrivAESWith3DESKeyExtension implements NonStandardSecurityProtocol {

  /**
   * Unique ID of this privacy protocol.
   */
  public static OID ID = new OID(SnmpConstants.oosnmpUsmAesCfb192ProtocolWith3DESKeyExtension);

  /**
   * Constructor.
   */
  public PrivAES192With3DESKeyExtension() {
    super(24);
  }

  @Override
  public OID getDefaultID() {
    return null;
  }
}
