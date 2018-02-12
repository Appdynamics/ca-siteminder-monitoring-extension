/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.security;

import org.snmp4j.TransportStateReference;

/**
 * The <code>TsmSecurityStateReference</code> holds cached security data
 * for the {@link TSM} security model.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public class TsmSecurityStateReference implements SecurityStateReference {

  private TransportStateReference tmStateReference;

  public TsmSecurityStateReference() {
  }

  public TransportStateReference getTmStateReference() {
    return tmStateReference;
  }

  public void setTmStateReference(TransportStateReference tmStateReference) {
    this.tmStateReference = tmStateReference;
  }

  @Override
  public String toString() {
    return "TsmSecurityStateReference[" +
        "tmStateReference=" + tmStateReference +
        ']';
  }
}
