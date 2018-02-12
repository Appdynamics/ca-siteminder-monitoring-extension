/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * The <code>EnumerationIterator</code> provides an iterator from an
 * {@link Enumeration}.
 *
 * @author Frank Fock
 * @version 2.0
 * @since 1.6.1
 */
public class EnumerationIterator<E> implements Iterator<E> {

  private Enumeration<E> e;

  public EnumerationIterator(Enumeration<E> e) {
    this.e = e;
  }

  /**
   * Returns <tt>true</tt> if the iteration has more elements.
   *
   * @return <tt>true</tt> if the iterator has more elements.
   */
  public boolean hasNext() {
    return e.hasMoreElements();
  }

  /**
   * Returns the next element in the iteration.
   *
   * @return the next element in the iteration.
   */
  public E next() {
    return e.nextElement();
  }

  /**
   * This method is not supported for enumerations.
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
