/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.transport;

import org.snmp4j.MessageDispatcher;
import org.snmp4j.TransportMapping;
import org.snmp4j.TransportStateReference;
import org.snmp4j.smi.Address;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>AbstractTransportMapping</code> provides an abstract
 * implementation for the message dispatcher list and the maximum inbound
 * message size.
 *
 * @author Frank Fock
 * @version 2.0
 */
public abstract class AbstractTransportMapping<A extends Address>
    implements TransportMapping<A> {

  protected List<TransportListener> transportListener = new ArrayList<TransportListener>(1);
  protected int maxInboundMessageSize = (1 << 16) - 1;
  protected boolean asyncMsgProcessingSupported = true;

  public abstract Class<? extends Address> getSupportedAddressClass();

  public abstract void sendMessage(A address, byte[] message,
                                   TransportStateReference tmStateReference)
      throws IOException;

  public synchronized void addTransportListener(TransportListener l) {
    if (!transportListener.contains(l)) {
      List<TransportListener> tlCopy =
          new ArrayList<TransportListener>(transportListener);
      tlCopy.add(l);
      transportListener = tlCopy;
    }
  }

  public synchronized void removeTransportListener(TransportListener l) {
    if (transportListener != null && transportListener.contains(l)) {
      List<TransportListener> tlCopy =
          new ArrayList<TransportListener>(transportListener);
      tlCopy.remove(l);
      transportListener = tlCopy;
    }
  }

  protected void fireProcessMessage(Address address,  ByteBuffer buf,
                                    TransportStateReference tmStateReference) {
    if (transportListener != null) {
      for (TransportListener aTransportListener : transportListener) {
        aTransportListener.processMessage(this, address, buf, tmStateReference);
      }
    }
  }


  public abstract void close() throws IOException;
  public abstract void listen() throws IOException;

  public int getMaxInboundMessageSize() {
    return maxInboundMessageSize;
  }

  /**
   * Returns <code>true</code> if asynchronous (multi-threaded) message
   * processing may be implemented. The default is <code>true</code>.
   *
   * @return
   *    if <code>false</code> is returned the
   *    {@link MessageDispatcher#processMessage(TransportMapping, Address, ByteBuffer, TransportStateReference)}
   *    method must not return before the message has been entirely processed.
   */
  public boolean isAsyncMsgProcessingSupported() {
    return asyncMsgProcessingSupported;
  }

  /**
   * Specifies whether this transport mapping has to support asynchronous
   * messages processing or not.
   *
   * @param asyncMsgProcessingSupported
   *    if <code>false</code> the {@link MessageDispatcher#processMessage(TransportMapping, Address, ByteBuffer, TransportStateReference)}
   *    method must not return before the message has been entirely processed,
   *    because the incoming message buffer is not copied before the message
   *    is being processed. If <code>true</code> the message buffer is copied
   *    for each call, so that the message processing can be implemented
   *    asynchronously.
   */
  public void setAsyncMsgProcessingSupported(
      boolean asyncMsgProcessingSupported) {
    this.asyncMsgProcessingSupported = asyncMsgProcessingSupported;
  }

}
