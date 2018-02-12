/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.mp;

/**
 * The <code>TimedMessageID</code> adds system time information to the message ID that allows
 * to measure response times and detect lost messages with SNMPv3.
 *
 * @author Frank Fock
 * @since 2.4.3
 */
public class TimedMessageID extends SimpleMessageID {

  private static final long serialVersionUID = 952343921331667512L;

  private long creationNanoTime;

  public TimedMessageID(int messageID) {
    super(messageID);
    this.creationNanoTime = System.nanoTime();
  }

  /**
   * Gets the {@link System#nanoTime()} when this message ID object has been created.
   * @return
   *    the creation time stamp in nano seconds.
   */
  public long getCreationNanoTime() {
    return creationNanoTime;
  }

  @Override
  public String toString() {
    return "TimedMessageID{" +
        "msgID="+getID()+
        ",creationNanoTime=" + creationNanoTime +
        "}";
  }
}
