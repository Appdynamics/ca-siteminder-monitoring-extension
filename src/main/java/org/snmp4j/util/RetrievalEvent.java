/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.util;

import org.snmp4j.PDU;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.VariableBinding;

import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;

/**
 * The <code>RetrievalEvent</code> is an abstract class representing the result
 * of one or more GET/GETNEXT/GETBULK requests.
 *
 * @author Frank Fock
 * @version 1.8
 * @since 1.8
 */
public abstract class RetrievalEvent extends EventObject {

  /**
   * Retrieval operation was successful.
   */
  public static final int STATUS_OK = SnmpConstants.SNMP_ERROR_SUCCESS;
  /**
   * A request to the agent timed out.
   */
  public static final int STATUS_TIMEOUT = SnmpConstants.SNMP_ERROR_TIMEOUT;
  /**
   * The agent failed to return the objects in lexicographic order.
   */
  public static final int STATUS_WRONG_ORDER = SnmpConstants.SNMP_ERROR_LEXICOGRAPHIC_ORDER;
  /**
   * A report has been received from the agent.
   * @see #getReportPDU()
   */
  public static final int STATUS_REPORT = SnmpConstants.SNMP_ERROR_REPORT;
  /**
   * An exception occurred during retrieval operation.
   * @see #getException()
   */
  public static final int STATUS_EXCEPTION = -4;

  protected VariableBinding[] vbs;
  protected int status = STATUS_OK;
  protected Object userObject;
  protected Exception exception;
  protected PDU reportPDU;

  protected RetrievalEvent(EventListener source, Object userObject) {
    super(source);
    this.userObject = userObject;
  }

  /**
   * Creates a retrieval event with a status.
   * @param source
   *    the source of the event.
   * @param userObject
   *    the user object or <code>null</code>.
   * @param status
   *    one of the status constants defined for this object.
   */
  public RetrievalEvent(TreeUtils.TreeRequest source, Object userObject, int status) {
    this(source, userObject);
    this.status = status;
  }

  /**
   * Creates a retrieval event with an exception.
   * @param source
   *    the source of the event.
   * @param userObject
   *    the user object or <code>null</code>.
   * @param exception
   *    an exception instance.
   */
  public RetrievalEvent(TreeUtils.TreeRequest source, Object userObject, Exception exception) {
    this(source, userObject);
    this.exception = exception;
    this.status = STATUS_EXCEPTION;
  }

  /**
   * Creates a retrieval event with a report PDU.
   * @param source
   *    the source of the event.
   * @param userObject
   *    the user object or <code>null</code>.
   * @param report
   *    a PDU of type {@link PDU#REPORT}.
   */
  public RetrievalEvent(TreeUtils.TreeRequest source, Object userObject, PDU report) {
    this(source, userObject);
    this.reportPDU = report;
    this.status = STATUS_REPORT;
  }

  /**
   * Creates a retrieval event with row data.
   *
   * @param source
   *    the source of the event.
   * @param userObject
   *    the user object or <code>null</code>.
   * @param variableBindings
   *    an array of <code>VariableBinding</code> instances.
   */
  public RetrievalEvent(ResponseListener source, Object userObject,
                        VariableBinding[] variableBindings) {
    this(source, userObject);
    this.vbs = variableBindings;
  }

  /**
   * Gets the status of the table operation.
   * @return
   *    one of the status constants defined for this object.
   *    {@link #STATUS_OK} indicates success, all other values indicate
   *    failure of the operation which corresponds to a SNMP error status
   *    as defined by {@link PDU#getErrorStatus()}.
   */
  public int getStatus() {
    return status;
  }

  /**
   * Indicates whether the event reports an error or not.
   * @return
   *    <code>true</code> if the operation failed with an error.
   */
  public boolean isError() {
    return (status != STATUS_OK);
  }

  /**
   * Gets the user object that has been specified by the user when the retrieval
   * operation that fired this event has been requested.
   * @return
   *    an object instance if an user object has been specified or
   *    <code>null</code> otherwise.
   */
  public Object getUserObject() {
    return userObject;
  }

  /**
   * Gets the exception associated with this event.
   * @return
   *    an Exception instance if there has been an exception instance
   *    associated with this event ({@link #getStatus()} returns
   *    {@link #STATUS_EXCEPTION}), or <code>null</code> otherwise.
   */
  public Exception getException() {
    return exception;
  }

  /**
   * Gets the report PDU associated with this event.
   * @return
   *    a <code>ScopedPDU</code> instance if there has been a report PDU
   *    instance associated with this event ({@link #getStatus()} returns
   *    {@link #STATUS_REPORT}), or <code>null</code> otherwise.
   */
  public PDU getReportPDU() {
    return reportPDU;
  }

  /**
   * Returns a textual error message for the error.
   * @return
   *    an error message or an empty string if no error occurred.
   */
  public String getErrorMessage() {
    switch (status) {
      case STATUS_EXCEPTION: {
        return exception.getMessage();
      }
      case STATUS_REPORT: {
        return "Report: "+reportPDU.get(0);
      }
      case STATUS_TIMEOUT: {
        return "Request timed out.";
      }
      case STATUS_WRONG_ORDER: {
        return "Agent did not return variable bindings in lexicographic order.";
      }
      default: {
        return PDU.toErrorStatusText(status);
      }
    }
  }

  public String toString() {
    return getClass().getName()+"[vbs="+
        ((vbs == null) ? "null" : ""+Arrays.asList(vbs))+
        ",status="+status+",exception="+
        exception+",report="+reportPDU+"]";
  }

}
