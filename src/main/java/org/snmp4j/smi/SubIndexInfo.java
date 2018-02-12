/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.smi;

/**
 * The {@link SubIndexInfo} interface represents the meta information of a SMI INDEX clause element (= sub-index)
 * which are relevant for converting an OID index value to an INDEX object and vice versa.
 *
 * @author Frank Fock
 * @since 2.5.0
 */
public interface SubIndexInfo {
  /**
   * Checks if the sub-index represented by this index info has an implied length or not.
   *
   * @return <code>true</code> if the length of this variable length sub-index is implied (i.e., the sub-index
   * is the last in the index).
   */
  boolean hasImpliedLength();

  /**
   * Gets the minimum length in bytes of the sub-index. If min and max length are equal, then this sub-index
   * is a fixed length length sub-index.
   *
   * @return the minimum length of the (sub-index) in bytes.
   */
  int getMinLength();

  /**
   * Gets the maximum length in bytes of the sub-index. If min and max length are equal, then this sub-index
   * is a fixed length length sub-index.
   *
   * @return the maximum length of the (sub-index) in bytes.
   */
  int getMaxLength();

  /**
   * Gets the SNMP syntax value of the sub-index' base syntax.
   *
   * @return the SNMP4J (BER) syntax identifier.
   */
  int getSnmpSyntax();
}
