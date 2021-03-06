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
import java.net.UnknownHostException;

/**
 * The <code>SshAddress</code> represents a SSH transport addresses as defined
 * by RFC 5592 SnmpSSHAddress textual convention.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 2.0
 */
public class SshAddress extends TcpAddress {

  static final long serialVersionUID = 0L;

  private static final LogAdapter logger = LogFactory.getLogger(SshAddress.class);

  private String addressURI;
  private String userName;

  public SshAddress(String addressURI) {
    this.addressURI = addressURI;
    parseAddress(addressURI);
  }

  public SshAddress(InetAddress inetAddress, int port) {
    super(inetAddress, port);
    addressURI = ""+inetAddress.getHostAddress()+':'+port;
  }

  public SshAddress(InetAddress inetAddress, int port, String userName) {
    super(inetAddress, port);
    this.userName = userName;
    addressURI = userName+'@'+inetAddress.getHostAddress()+':'+port;
  }

  public String getAddressURI() {
    return addressURI;
  }

  public String getUserName() {
    return userName;
  }

  @Override
  public boolean parseAddress(String address) {
    try {
      String addressString = address;
      String portString = null;
      String userName = null;
      int lastColon = address.lastIndexOf(':');
      if ((lastColon >= 0) && (lastColon+1 < address.length())) {
        addressString = address.substring(0, lastColon);
        portString = address.substring(lastColon+1);
      }
      int firstAtPos = addressString.indexOf('@');
      if ((firstAtPos > 0) && (firstAtPos+1 < addressString.length())) {
        userName = addressString.substring(0, firstAtPos);
        addressString = addressString.substring(firstAtPos+1);
      }
      try {
        setInetAddress(InetAddress.getByName(addressString));
        this.port = Integer.parseInt(portString);
        this.userName = userName;
      }
      catch (UnknownHostException uhex) {
        return false;
      }
      return true;
    }
    catch (Exception ex) {
      logger.error("Failed to parse address '"+address+"' as SSH address: "+ex.getMessage(), ex);
      return false;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    SshAddress that = (SshAddress) o;

    if (!getInetAddress().equals(that.getInetAddress())) return false;
    if (!addressURI.equals(that.addressURI)) return false;
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + addressURI.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SshAddress[" +
        "addressURI='" + addressURI + '\'' +
        ']';
  }
}
