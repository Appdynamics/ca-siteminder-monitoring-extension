/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.transport;

import org.snmp4j.MessageException;

/**
 * The <code>UnsupportedAddressClassException</code> indicates a message
 * exception caused by unsupported address class. When this exception is
 * thrown, the target address class is not supported by the entity that
 * is to sent the message and that operation will be canceled.
 *
 * @author Frank Fock
 * @version 1.6
 */
public class UnsupportedAddressClassException extends MessageException {

  private static final long serialVersionUID = -864696255672171900L;

  private Class addressClass;

  public UnsupportedAddressClassException(String message, Class addressClass) {
    super(message);
    this.addressClass = addressClass;
  }

  /**
   * Returns the class of the address class that is not supported.
   * @return
   *    a Class.
   */
  public Class getAddressClass() {
    return addressClass;
  }
}
