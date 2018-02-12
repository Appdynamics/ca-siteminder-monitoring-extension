/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.smi;

/**
 * The {@link SubIndexInfoImpl} class represents the meta information of a SMI INDEX clause element (= sub-index)
 * which are relevant for converting an OID index value to an INDEX object and vice versa.
 *
 * @author Frank Fock
 * @since 2.5.0
 */
public class SubIndexInfoImpl implements SubIndexInfo {

  private boolean impliedLength;
  private int minLength;
  private int maxLength;
  private int snmpSyntax;

  /**
   * Create a sub index information object.
   * @param impliedLength
   *    indicates if the sub-index value has an implied variable length (must apply to the last variable length
   *    sub-index only).
   * @param minLength
   *    the minimum length in bytes of the sub-index value.
   * @param maxLength
 *      the maximum length in bytes of the sub-index value.
   * @param snmpSyntax
   *    the BER syntax of the sub-index object type's base syntax.
   */
  public SubIndexInfoImpl(boolean impliedLength, int minLength, int maxLength, int snmpSyntax) {
    this.impliedLength = impliedLength;
    this.maxLength = maxLength;
    this.minLength = minLength;
    this.snmpSyntax = snmpSyntax;
  }

  @Override
  public boolean hasImpliedLength() {
    return impliedLength;
  }

  @Override
  public int getMinLength() {
    return minLength;
  }

  @Override
  public int getMaxLength() {
    return maxLength;
  }

  @Override
  public int getSnmpSyntax() {
    return snmpSyntax;
  }

}
