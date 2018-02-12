/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.transport.ssh;

import org.snmp4j.TransportStateReference;
import org.snmp4j.transport.TransportListener;

/**
 * The <code>SshSession</code> interface provides access to a SSH session
 * provided by a {@link SshTransportAdapter}.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public interface SshSession<I> {

  Long getID();

  TransportStateReference getTransportStateReference();

  void setTransportStateReference(TransportStateReference tmStateReference);

  I getImplementation();

  void addTransportListener(TransportListener transportListener);
  void removeTransportListener(TransportListener transportListener);
}
