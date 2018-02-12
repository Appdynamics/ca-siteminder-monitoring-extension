/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.transport.ssh;

import org.snmp4j.TransportStateReference;

/**
 * The <code>SshTransportAdapter</code> adapts 3rd party SSH transport protocol
 * implementations for SNMP4J.
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public interface SshTransportAdapter<I> {

  SshSession<I> openClientSession(TransportStateReference tmStateReference, int maxMessageSize);
  SshSession<I> openServerSession(TransportStateReference tmStateReference, int maxMessageSize);

  boolean closeSession(Long sessionID);

}
