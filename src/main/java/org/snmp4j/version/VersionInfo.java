/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */
package org.snmp4j.version;

/**
 * The <code>VersionInfo</code> object returns information about the version
 * of this SNMP4J release.
 *
 * @author Frank Fock
 * @version 2.0.0
 * @since 1.9.1e
 */
public class VersionInfo {

  public static final int MAJOR = 2;
  public static final int MINOR = 5;
  public static final int UPDATE = 0;
  public static final String PATCH = "";

  public static final String VERSION =
      MAJOR + "." + MINOR + "." + UPDATE + PATCH;

  /**
   * Gets the version string for this release.
   * @return
   *    a string of the form <code>major.minor.update[patch]</code>.
   */
  public static String getVersion() {
    return VERSION;
  }

  private VersionInfo() {
  }

}
