/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.smi;

import java.net.InetAddress;

/**
 * The <code>UdpAddress</code> represents UDP/IP transport addresses.
 * @author Frank Fock
 * @version 1.8.3
 */
public class UdpAddress extends TransportIpAddress {

  static final long serialVersionUID = -4390734262648716203L;

  public UdpAddress() {
  }

  public UdpAddress(InetAddress inetAddress, int port) {
    setInetAddress(inetAddress);
    setPort(port);
  }

  public UdpAddress(int port) {
    setPort(port);
  }

  public UdpAddress(String address) {
    if (!parseAddress(address)) {
      throw new IllegalArgumentException(address);
    }
  }

  public static Address parse(String address) {
    UdpAddress a = new UdpAddress();
    if (a.parseAddress(address)) {
      return a;
    }
    return null;
  }

  public boolean equals(Object o) {
    return (o instanceof UdpAddress) && super.equals(o);
  }

}

