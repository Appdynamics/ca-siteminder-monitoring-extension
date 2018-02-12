/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.smi;

/**
 * The <code>AssignableFromByteArray</code> interface describes objects whose
 * value can be set from a byte array and converted back to a byte array.
 *
 * @author Frank Fock
 * @version 1.8
 * @since 1.7
 */
public interface AssignableFromByteArray {

  /**
   * Sets the value of this object from the supplied byte array.
   * @param value
   *    a byte array.
   */
  void setValue(byte[] value);

  /**
   * Returns the value of this object as a byte array.
   * @return
   *    a byte array.
   * @since 1.8
   */
  byte[] toByteArray();
}
