/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.smi;

/**
 * The <code>VariantVariableCallback</code> can be implemented by
 * objects that want to intercept/monitor reading and writing of
 * a <code>VariantVariable</code>'s value.
 *
 * @author Frank Fock
 * @version 1.7
 */
public interface VariantVariableCallback {

  /**
   * The supplied variable's value has been updated.
   * @param variable
   *    the <code>VariantVariable</code> that has been updated.
   */
  void variableUpdated(VariantVariable variable);

  /**
   * The supplied variable needs to be updated because it is about
   * to be read.
   * @param variable
   *    the <code>VariantVariable</code> that will be read.
   */
  void updateVariable(VariantVariable variable);

}
