/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.mp;

/**
 * The <code>MutableStateReference</code> encapsulates a {@link StateReference}
 * for read/write access.
 * @author Frank Fock
 * @version 1.0
 */
public class MutableStateReference {

  private StateReference stateReference;

  public MutableStateReference() {
  }

  public StateReference getStateReference() {
    return stateReference;
  }
  public void setStateReference(StateReference stateReference) {
    this.stateReference = stateReference;
  }

}
