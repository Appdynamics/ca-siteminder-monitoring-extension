/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package org.snmp4j.uri;

import java.net.URI;

/**
 * The <code>SnmpUriCallback</code> interface is used by asynchronous
 * methods of the {@link SnmpURI} class to provide instances of 
 * {@link SnmpUriResponse} to the caller.
 * 
 * @author Frank Fock
 * @since 2.1
 */
public interface SnmpUriCallback {

  /**
   * Process a response on the request
   * @param response
   *    a {@link SnmpUriResponse} instance with some or all
   *    of the requested data or an error status.
   *    If the {@link SnmpUriResponse#getResponseType()}
   *    is {@link SnmpUriResponse.Type#NEXT} then
   *    additional calls for this request will follow, otherwise not.
   * @param url
   *    the URI that was used as request for this response.
   * @param userObject
   *    an arbitrary object provided on the asynchronous call
   *    on the request processor.
   * @return
   *    <code>true</code> if the request should be cancelled,
   *    <code>false</code> otherwise.
   */
  boolean onResponse(SnmpUriResponse response, URI url, Object userObject);
  
}

