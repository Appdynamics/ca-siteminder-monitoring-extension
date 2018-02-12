/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.transport;

import org.snmp4j.smi.Address;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * The <code>TransportStateEvent</code> describes a state change for
 * a transport connection. Optionally, connection establishment can be
 * cancelled.
 *
 * @author Frank Fock
 * @version 2.4
 * @since 1.7
 */
public class TransportStateEvent extends EventObject {

  private static final long serialVersionUID = 6440139076579035559L;

  public static final int STATE_UNKNOWN = 0;
  public static final int STATE_CONNECTED = 1;
  public static final int STATE_DISCONNECTED_REMOTELY = 2;
  public static final int STATE_DISCONNECTED_TIMEOUT = 3;
  public static final int STATE_CLOSED = 4;

  private int newState;
  private Address peerAddress;
  private IOException causingException;
  private ArrayList<byte[]> discardedMessages;

  private boolean cancelled = false;

  public TransportStateEvent(TcpTransportMapping source,
                             Address peerAddress,
                             int newState,
                             IOException causingException) {
    super(source);
    this.newState = newState;
    this.peerAddress = peerAddress;
    this.causingException = causingException;
  }

  public TransportStateEvent(TcpTransportMapping source,
                             Address peerAddress,
                             int newState,
                             IOException causingException,
                             List<byte[]> discardedMessages) {
    this(source, peerAddress, newState, causingException);
    this.discardedMessages = new ArrayList<byte[]>(discardedMessages);
  }

  public IOException getCausingException() {
    return causingException;
  }

  public int getNewState() {
    return newState;
  }

  public Address getPeerAddress() {
    return peerAddress;
  }

  /**
   * Gets the messages that were discarded due to a state change of the transport connection.
   * @return
   *    a (possibly empty) list of messages that were discarded or <code>null</code> if the event has not terminated
   *    the transport connection.
   * @since 2.4.0
   */
  public List<byte[]> getDiscardedMessages() {
    return discardedMessages;
  }

  /**
   * Indicates whether this event has been canceled. Only
   * {@link #STATE_CONNECTED} events can be canceled.
   * @return
   *    <code>true</code> if the event has been canceled.
   * @since 1.8
   */
  public boolean isCancelled() {
    return cancelled;
  }

  public String toString() {
    return TransportStateEvent.class.getName()+"[source="+source+
        ",peerAddress="+peerAddress+
        ",newState="+newState+
        ",cancelled="+cancelled+
        ",causingException="+causingException+"]";
  }

  /**
   * Sets the canceled state of the transport event. Only
   * {@link #STATE_CONNECTED} events can be canceled.
   * @param cancelled
   *    <code>true</code> if the event should be canceled, i.e. a connection
   *    attempt should be rejected.
   * @since 1.8
   */
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
}
