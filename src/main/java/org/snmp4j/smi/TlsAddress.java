/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.smi;

import org.snmp4j.log.LogAdapter;
import org.snmp4j.log.LogFactory;

import java.net.InetAddress;

/**
 * The <code>SshAddress</code> represents a SSH transport addresses as defined
 * by RFC 5953 SnmpTSLAddress textual convention.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public class TlsAddress extends TcpAddress {

  static final long serialVersionUID = 0L;

  private static final LogAdapter logger = LogFactory.getLogger(SshAddress.class);

  public TlsAddress() {
    super();
  }

  public TlsAddress(InetAddress inetAddress, int port) {
    super(inetAddress, port);
  }

  public TlsAddress(String address) {
    if (!parseAddress(address)) {
      throw new IllegalArgumentException(address);
    }
  }

  public static Address parse(String address) {
    try {
      TlsAddress a = new TlsAddress();
      if (a.parseAddress(address)) {
        return a;
      }
    }
    catch (Exception ex) {
      logger.error(ex);
    }
    return null;
  }

  public boolean equals(Object o) {
    return (o instanceof TlsAddress) && super.equals(o);
  }

}

