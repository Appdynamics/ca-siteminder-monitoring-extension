/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */




package org.snmp4j.security;

/**
 * Parameter class for encrypt and decrypt methods of {@link SecurityProtocol}.
 * @author Jochen Katz
 * @version 1.0
 */
public class DecryptParams
{
  /**
   * Initialize with the given value.
   * @param array
   *    the array as received on the wire
   * @param offset
   *    offset within the array
   * @param length
   *    length of the decrypt params
   */
  public DecryptParams(byte[] array, int offset, int length)
  {
    this.array = array;
    this.offset = offset;
    this.length = length;
  }

  /**
   * Inizialize with null values.
   */
  public DecryptParams()
  {
    this.array = null;
    this.offset = 0;
    this.length = 0;
  }

  /**
   * Initialize with the given value.
   * @param array
   *    the array as received on the wire
   * @param offset
   *    offset within the array
   * @param length
   *    length of the decrypt params
   */
  public void setValues(byte[] array, int offset, int length)
  {
    this.array = array;
    this.offset = offset;
    this.length = length;
  }

  public byte[] array;
  public int offset;
  public int length;
}
