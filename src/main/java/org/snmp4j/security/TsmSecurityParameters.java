/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.security;

import org.snmp4j.asn1.BERInputStream;
import org.snmp4j.smi.OctetString;

import java.io.IOException;

/**
 * {@link SecurityParameters} implementation for the {@link TSM}
 * security model.
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public class TsmSecurityParameters extends OctetString implements SecurityParameters {

  private int securityParametersPosition;
  private int decodedLength = -1;

  public TsmSecurityParameters() {
    super();
  }

  @Override
  public int getSecurityParametersPosition() {
    return securityParametersPosition;
  }

  @Override
  public void setSecurityParametersPosition(int pos) {
    this.securityParametersPosition = pos;
  }

  @Override
  public int getBERMaxLength(int securityLevel) {
    return getBERLength();
  }

  @Override
  public void decodeBER(BERInputStream inputStream) throws IOException {
    long startPos = inputStream.getPosition();
    super.decodeBER(inputStream);
    decodedLength = (int) (inputStream.getPosition() - startPos);
  }

  /**
   * Gets the position of the {@link org.snmp4j.ScopedPDU}.
   *
   * @return
   *    the start position in the {@link BERInputStream}.
   */
  public int getScopedPduPosition() {
    if (decodedLength >= 0) {
      return decodedLength + getSecurityParametersPosition();
    }
    else {
      return getSecurityParametersPosition()+getBERLength();
    }
  }

}
