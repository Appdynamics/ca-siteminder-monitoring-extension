/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.security;

import org.snmp4j.asn1.BER;
import org.snmp4j.asn1.BERInputStream;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The abstract class <code>SNMPv3SecurityModel</code> implements
 * common methods and fields for security models for the SNMPv3
 * message processing model.
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public abstract class SNMPv3SecurityModel implements SecurityModel {

  protected OctetString localEngineID;

  /**
   * Returns the local engine ID.
   * @return
   *    the local engine ID.
   * @since 1.2
   */
  public OctetString getLocalEngineID() {
    return localEngineID;
  }

  protected static byte[] buildWholeMessage(Integer32 snmpVersion,
                                            byte[] scopedPdu,
                                            byte[] globalData,
                                            SecurityParameters securityParameters)
      throws IOException
  {
    int length =
        snmpVersion.getBERLength() +
        globalData.length +
        securityParameters.getBERLength() +
        scopedPdu.length;
    int totalLength = BER.getBERLengthOfLength(length) + length + 1;

    ByteArrayOutputStream os = new ByteArrayOutputStream(totalLength);
    BER.encodeHeader(os, BER.SEQUENCE, length);
    snmpVersion.encodeBER(os);
    os.write(globalData);
    securityParameters.encodeBER(os);
    os.write(scopedPdu);
    int secParamsPos = 1 + snmpVersion.getBERLength() +
        BER.getBERLengthOfLength(length)  + globalData.length;
    securityParameters.setSecurityParametersPosition(secParamsPos);
    return os.toByteArray();
  }

  protected static byte[] buildMessageBuffer(BERInputStream scopedPDU)
      throws IOException
  {
    scopedPDU.mark(16);
    int readLengthBytes = (int)scopedPDU.getPosition();
    BER.MutableByte mutableByte = new BER.MutableByte();
    int length = BER.decodeHeader(scopedPDU, mutableByte);
    readLengthBytes = (int)scopedPDU.getPosition() - readLengthBytes;
    byte[] buf = new byte[length + readLengthBytes];
    scopedPDU.reset();

    int offset = 0;
    int avail = scopedPDU.available();
    while ((offset < buf.length) && (avail > 0)) {
      int read = scopedPDU.read(buf, offset, buf.length - offset);
      if (read < 0) {
        break;
      }
      offset += read;
    }
    return buf;
  }

}
