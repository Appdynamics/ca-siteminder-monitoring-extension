/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.smi;

/**
 * This abstract class helps to implement a {@link VariantVariableCallback}
 * for a read-only Variable.
 *
 * @author Frank Fock
 * @version 1.7
 * @since 1.7
 */
public abstract class ReadonlyVariableCallback implements VariantVariableCallback {

  public ReadonlyVariableCallback() {
  }

  public abstract void updateVariable(VariantVariable variable);

  public final void variableUpdated(VariantVariable variable) {
  }
}
