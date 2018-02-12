/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.smi;

/**
 * The <code>Address</code> interface serves as a base class for all SNMP
 * transport addresses.
 * <p>
 * Note: This class should be moved to package <code>org.snmp4j</code>
 * in SNMP4J 2.0.
 * </p>
 *
 * @author Frank Fock
 * @version 2.0
 */
public interface Address extends AssignableFromString, AssignableFromByteArray {

  /**
   * Checks whether this <code>Address</code> is a valid transport address.
   * @return
   *    <code>true</code> if the address is valid, <code>false</code> otherwise.
   */
  boolean isValid();

  /**
   * Parses the address from the supplied string representation.
   * @param address
   *    a String representation of this address.
   * @return
   *    <code>true</code> if <code>address</code> could be successfully
   *    parsed and has been assigned to this address object, <code>false</code>
   *    otherwise.
   */
  boolean parseAddress(String address);

  /**
   * Sets the address value from the supplied String. The string must match
   * the format required for the Address instance implementing this interface.
   * Otherwise an {@link IllegalArgumentException} runtime exception is thrown.
   * @param address
   *    an address String.
   * @since 1.7
   */
  void setValue(String address);
}

