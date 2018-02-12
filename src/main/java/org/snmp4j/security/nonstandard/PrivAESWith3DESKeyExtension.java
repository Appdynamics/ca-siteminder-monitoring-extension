/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security.nonstandard;

import org.snmp4j.log.LogAdapter;
import org.snmp4j.log.LogFactory;
import org.snmp4j.security.AuthenticationProtocol;
import org.snmp4j.security.PrivAES;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;

/**
 * This class is provided for interoperability with some broken AES implementations of major
 * network device manufactures which use a key extension algorithm that was specified for
 * {@link org.snmp4j.security.Priv3DES} but was never specified for AES 192 and 256 bit.
 *
 * Note: DO NOT USE THIS CLASS IF YOU WANT TO COMPLY WITH draft-blumenthal-aes-usm-04.txt!
 *
 * @author Frank Fock
 * @version 2.2.3
 * @since 2.2.3
 */
public abstract class PrivAESWith3DESKeyExtension extends PrivAES implements NonStandardSecurityProtocol {

  private static final LogAdapter logger = LogFactory.getLogger(PrivAESWith3DESKeyExtension.class);

  protected OID oid;

  /**
   * Constructor.
   *
   * @param keyBytes
   *    Length of key, must be 16, 24 or 32.
   * @throws IllegalArgumentException
   *    if keyBytes is illegal
   */
  public PrivAESWith3DESKeyExtension(int keyBytes) {
    super(keyBytes);
  }

  @Override
  public byte[] extendShortKey(byte[] shortKey, OctetString password, byte[] engineID, AuthenticationProtocol authProtocol) {
    int length = shortKey.length;
    byte[] extendedKey = new byte[getMinKeyLength()];
    System.arraycopy(shortKey, 0, extendedKey, 0, shortKey.length);

    byte[] key = new byte[getMinKeyLength()];
    System.arraycopy(shortKey, 0, key, 0, shortKey.length);
    while (length < getMinKeyLength()) {
      key = authProtocol.passwordToKey(new OctetString(key, 0, length), engineID);
      int copyBytes = Math.min(getMinKeyLength() - length,
          authProtocol.getDigestLength());
      System.arraycopy(key, 0, extendedKey, length, copyBytes);
      length += copyBytes;
    }
    if (logger.isDebugEnabled()) {
       logger.debug("AES nonstandard key extend produced key "+new OctetString(extendedKey).toHexString());
    }
    return extendedKey;
  }

  @Override
  public void setID(OID newOID) {
    this.oid = newOID;
  }

  @Override
  public OID getID() {
    return (oid == null) ? getDefaultID() : oid;
  }
}
