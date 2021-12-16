/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * The <code>Log4jLogFactory</code> implements a SNMP4J LogFactory for
 * Log4J. In order to use Log4J for logging SNMP4J log messages the
 * static {@link LogFactory#setLogFactory} method has to be used before
 * any SNMP4J class is referenced or instantiated.
 *
 * @author Frank Fock
 * @version 1.6.1
 * @since 1.2.1
 */
public class Log4jLogFactory extends LogFactory {

  public Log4jLogFactory() {
  }

  protected LogAdapter createLogger(Class c) {
    return new Log4jLogAdapter(LogManager.getLogger(c));
  }

  protected LogAdapter createLogger(String className) {
    return new Log4jLogAdapter(LogManager.getLogger(className));
  }

  public LogAdapter getRootLogger() {
    return new Log4jLogAdapter(LogManager.getRootLogger());
  }

  @SuppressWarnings("unchecked")
  public Iterator loggers() {
    LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
    ArrayList<Logger> l = new ArrayList<>(loggerContext.getLoggers());
    ArrayList<Log4jLogAdapter> la = new ArrayList<>(l.size());
    for (Logger logger : l) {
      la.add(new Log4jLogAdapter(logger));
    }
    Collections.sort(la);
    return la.iterator();
  }
}
