/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * The <code>Log4jLogAdapter</code> implements a logging adapter for Log4J.
 *
 * @author Frank Fock
 * @version 1.10
 * @since 1.2.1
 */
public class Log4jLogAdapter implements LogAdapter, Comparable {

  private final Logger logger;
  private final static String FQCN = Log4jLogAdapter.class.getName();

  /**
   * Creates a Log4J log adapter from a Log4J Logger.
   * @param logger
   *    the Log4J Logger to use with this adapter.
   * @since 1.2.1
   */
  public Log4jLogAdapter(Logger logger) {
    this.logger = logger;
  }

  /**
   * Logs a debug message.
   *
   * @param message the message to log.
   */
  public void debug(Serializable message) {
    logger.log(Level.DEBUG, message, null);
  }

  /**
   * Logs an error message.
   *
   * @param message the message to log.
   */
  public void error(Serializable message) {
    logger.log(Level.ERROR, message, null);
  }

  /**
   * Logs an error message.
   *
   * @param message the message to log.
   * @param throwable the exception that caused to error.
   */
  public void error(CharSequence message, Throwable throwable) {
    logger.log(Level.ERROR, message, throwable);
  }

  /**
   * Logs an informational message.
   *
   * @param message the message to log.
   */
  public void info(CharSequence message) {
    logger.log(Level.INFO, message, null);
  }

  /**
   * Checks whether DEBUG level logging is activated for this log adapter.
   *
   * @return <code>true</code> if logging is enabled or <code>false</code>
   *   otherwise.
   */
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  /**
   * Checks whether INFO level logging is activated for this log adapter.
   *
   * @return <code>true</code> if logging is enabled or <code>false</code>
   *   otherwise.
   */
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  /**
   * Checks whether WARN level logging is activated for this log adapter.
   *
   * @return <code>true</code> if logging is enabled or <code>false</code>
   *   otherwise.
   */
  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }

  /**
   * Logs an warning message.
   *
   * @param message the message to log.
   */
  public void warn(Serializable message) {
    logger.log(Level.WARN, message, null);
  }

  public void fatal(Object message) {
    logger.log(Level.FATAL, message, null);
  }

  public void fatal(CharSequence message, Throwable throwable) {
    logger.log(Level.FATAL, message, throwable);
  }

  public void setLogLevel(LogLevel level) {
    Level l;
    switch (level.getLevel()) {
      case LogLevel.LEVEL_OFF:
        l = Level.OFF;
        break;
      case LogLevel.LEVEL_ALL:
        l = Level.ALL;
        break;
      case LogLevel.LEVEL_TRACE:
        l = Level.TRACE;
        break;
      case LogLevel.LEVEL_DEBUG:
        l = Level.DEBUG;
        break;
      case LogLevel.LEVEL_INFO:
        l = Level.INFO;
        break;
      case LogLevel.LEVEL_WARN:
        l = Level.WARN;
        break;
      case LogLevel.LEVEL_ERROR:
        l = Level.ERROR;
        break;
      case LogLevel.LEVEL_FATAL:
        l = Level.FATAL;
        break;
      default:
        l = null;
    }
    Configurator.setLevel(logger.getName(),l);
  }

  public Logger getLogger() {
    return logger;
  }

  public String getName() {
    return logger.getName();
  }

  public LogLevel getLogLevel() {
    Level l = logger.getLevel();
    return toLogLevel(l);
  }

  private LogLevel toLogLevel(Level l) {
    if (l == null) {
      return LogLevel.NONE;
    }
    if(l.intLevel()==Level.OFF.intLevel()){
      return LogLevel.OFF;
    } else if(l.intLevel()==Level.ALL.intLevel()){
      return LogLevel.ALL;
    } else if(l.intLevel()==Level.DEBUG.intLevel()){
      return LogLevel.DEBUG;
    } else if(l.intLevel()==Level.INFO.intLevel()){
      return LogLevel.INFO;
    } else if(l.intLevel()==Level.WARN.intLevel()){
      return LogLevel.WARN;
    } else if(l.intLevel()==Level.ERROR.intLevel()){
      return LogLevel.ERROR;
    } else if(l.intLevel()==Level.FATAL.intLevel()){
      return LogLevel.FATAL;
    } else{
      return LogLevel.DEBUG;
    }
  }

  public int compareTo(Object o) {
    return getName().compareTo(((Log4jLogAdapter)o).getName());
  }

  public LogLevel getEffectiveLogLevel() {
    Level l = logger.getLevel();
    return toLogLevel(l);
  }

  @SuppressWarnings("unchecked")
  public Iterator<Appender> getLogHandler() {
    Map<String, Appender> appenderMap = ((org.apache.logging.log4j.core.Logger)logger).getAppenders();
    return (appenderMap.values()).iterator();
  }
}
