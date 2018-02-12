/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */




package org.snmp4j.smi;

/**
 * The <code>Gauge32</code> class is indistinguishable from
 * <code>UnsingedInteger32</code>.
 *
 * @author Frank Fock
 * @version 1.0
 */
public class Gauge32 extends UnsignedInteger32 {

  static final long serialVersionUID = 1469573439175461445L;

  public Gauge32() {
  }

  public Gauge32(long value) {
    super(value);
  }

  public int getSyntax() {
    return SMIConstants.SYNTAX_GAUGE32;
  }

  public Object clone() {
    return new Gauge32(value);
  }

}

