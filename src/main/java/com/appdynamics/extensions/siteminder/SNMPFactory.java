/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;

import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeUtils;

import java.io.IOException;
import java.util.Map;

import static com.appdynamics.extensions.siteminder.Util.convertToString;

class SNMPFactory {

    private static final String DEFAULT_SNMP_VERSION = "2";
    private static final String DEFAULT_COMMUNITY_STRING = "public";
    private static final String DEFAULT_RETRIES = "5";
    private static final String DEFAULT_TIMEOUT = "5000";
    private static final String DEFAULT_MAX_REPETITIONS = "10";

    Snmp createSNMPSession() throws IOException {
        TransportMapping transportMapping = new DefaultUdpTransportMapping();
        transportMapping.listen();
        final Snmp snmp = new Snmp(transportMapping);
        return snmp;
    }

    SNMPWalker createWalker(Snmp snmp,Target target, Map config){
        TreeUtils treeUtils = new TreeUtils(snmp,new DefaultPDUFactory());
        treeUtils.setMaxRepetitions(Integer.parseInt(convertToString(config.get("maxRepetitions"),DEFAULT_MAX_REPETITIONS)));
        return new SNMPWalker(target,treeUtils);
    }

    Target createTarget(Map config){
        String version = getSnmpVersion(config);
        if(version.equalsIgnoreCase("3")){
            throw new UnsupportedOperationException("Version 3 is not supported");
        }
        else{
            return createCommunityTarget(version,config);
        }
    }

    private CommunityTarget createCommunityTarget(String version,Map config) {
        final CommunityTarget comTarget = new CommunityTarget();
        comTarget.setCommunity(new OctetString(convertToString(config.get("communityString"), DEFAULT_COMMUNITY_STRING)));
        comTarget.setVersion(getSnmpVersion(version));
        Address targetAddress = GenericAddress.parse("udp:"+ convertToString(config.get("host"),"") + "/" + convertToString(config.get("port"),""));
        comTarget.setAddress(targetAddress);
        comTarget.setRetries(Integer.parseInt(convertToString(config.get("retries"),DEFAULT_RETRIES)));
        comTarget.setTimeout(Long.parseLong(convertToString(config.get("timeout"),DEFAULT_TIMEOUT)));
        return comTarget;
    }

    private String getSnmpVersion(Map config) {
        return convertToString(config.get("snmpVersion"), DEFAULT_SNMP_VERSION);
    }

    private int getSnmpVersion(String version) {
        if(version.equalsIgnoreCase("1")){
            return SnmpConstants.version1;
        }
        if(version.equalsIgnoreCase("3")){
            return SnmpConstants.version3;
        }
        return SnmpConstants.version2c;
    }
}
