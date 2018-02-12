/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.smi;

/**
 * The <code>AssignableFromIntArray</code> interface describes objects whose
 * value can be set from an int array and converted back to an int array.
 *
 * @author Frank Fock
 * @version 1.10.1
 * @since 1.10.1
 */
public interface AssignableFromIntArray {

  /**
   * Sets the value of this object from the supplied int array.
   * @param value
   *    an int array.
   */
  void setValue(int[] value);

  /**
   * Returns the value of this object as an int array.
   * @return
   *    an int array.
   */
  int[] toIntArray();
}
