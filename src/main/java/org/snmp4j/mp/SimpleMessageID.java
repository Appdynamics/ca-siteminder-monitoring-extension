/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.mp;

import java.io.Serializable;

/**
 * The <code>SimpleMessageID</code> implements the simplest possible {@link MessageID} with
 * a minimum memory footprint.
 *
 * @author Frank Fock
 * @since 2.4.3
 */
public class SimpleMessageID implements MessageID, Serializable {

  private static final long serialVersionUID = 6301818691474165283L;

  private int messageID;

  public SimpleMessageID(int messageID) {
    this.messageID = messageID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SimpleMessageID that = (SimpleMessageID) o;

    if (messageID != that.messageID) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return messageID;
  }

  @Override
  public int getID() {
    return messageID;
  }

  @Override
  public String toString() {
    return Integer.toString(messageID);
  }
}
