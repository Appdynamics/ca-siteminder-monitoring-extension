/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.mp;

import org.snmp4j.event.CounterEvent;
import org.snmp4j.event.CounterListener;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import java.util.Hashtable;
import java.util.Map;

/**
 * The <code>DefaultCounterListener</code> is the default implementation of
 * the <code>CounterListener</code> interface. For any counter incrementation
 * event it checks whether the referenced counter object already exists. If not,
 * it will be created and initialized with one. Otherwise, the current value
 * will be incremented by one. In either case, the current value will be
 * returned in the event object.
 * <p>
 * To use a <code>DefaultCounterListener</code> with SNMP4J, add it to the
 * default <code>CounterSupport</code> by:
 * <pre>
 *   CounterSupport.getInstance().addCounterListener(new DefaultCounterListener());
 * </pre>
 *
 * @author Frank Fock
 * @version 2.0
 */
public class DefaultCounterListener implements CounterListener {

  private Map<OID, Counter32> counters = new Hashtable<OID, Counter32>();
  private boolean countRegisteredOnly;

  /**
   * Default constructor.
   */
  public DefaultCounterListener() {
  }

  public synchronized void incrementCounter(CounterEvent event) {
    OID id = event.getOid();
    Counter32 counter = counters.get(id);
    if (counter == null) {
      if (countRegisteredOnly) {
        return;
      }
      counter = new Counter32(event.getIncrement());
      counters.put(id, counter);
    }
    else {
      counter.increment(event.getIncrement());
    }
    // write back current value
    event.setCurrentValue((Variable) counter.clone());
  }

  /**
   * Adds a counter to this counter listener. If counter
   * events have been received already for the given <tt>oid</tt>
   * the provided counter will be incremented by the number of events
   * already counted and then <tt>counter</tt> replaces the old
   * counter.
   * @param oid
   *    the object identifier of the counter instance (thus including
   *    the .0 suffix).
   * @param counter
   *    the counter to register.
   * @return
   *    the counter previously associated with <tt>oid</tt> or
   *    <tt>null</tt> if no such counter exist.
   * @since 2.0
   */
  public synchronized Counter32 add(OID oid, Counter32 counter) {
    Counter32 oldCounter = counters.get(oid);
    if (oldCounter != null) {
      long value = oldCounter.getValue();
      counter.setValue(counter.getValue()+value);
    }
    return counters.put(oid, counter);
  }

  /**
   * Removes a counter from this listener and returns it.
   * @param oid
   *    the object identifier of the counter instance (thus including
   *    the .0 suffix).
   * @return
   *    the counter previously associated with <tt>oid</tt> or
   *    <tt>null</tt> if no such counter exist.
   * @since 2.0
   */
  public synchronized Counter32 remove(OID oid) {
    return counters.remove(oid);
  }

  public boolean isCountRegisteredOnly() {
    return countRegisteredOnly;
  }

  /**
   * Sets the flag which indicates how unregistered counter events
   * should be handled.
   * @param countRegisteredOnly
   *    if <tt>true</tt> counter events for OIDs which have not been
   *    added by {@link #add} will be ignored, otherwise a
   *    {@link Counter32} will be registered to count corresponding
   *    events.
   * @since 2.0
   */
  public void setCountRegisteredOnly(boolean countRegisteredOnly) {
    this.countRegisteredOnly = countRegisteredOnly;
  }
}
