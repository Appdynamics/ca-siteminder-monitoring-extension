/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;

import org.snmp4j.*;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeUtils;
import org.snmp4j.mp.MPv3;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.appdynamics.extensions.siteminder.Util.convertToString;

class SNMPFactory {

    //SNMP 1 & 2 constant
    private static final String DEFAULT_COMMUNITY_STRING = "public";

    //Overall SNMP constants
    private static final String DEFAULT_SNMP_VERSION = "2";
    private static final String DEFAULT_RETRIES = "5";
    private static final String DEFAULT_TIMEOUT = "5000";
    private static final String DEFAULT_MAX_REPETITIONS = "10";

    //SNMP3 specific constants
    private static final String DEFAULT_SECURITY_LEVEL = "1";
    private static final String DEFAULT_USERNAME_STRING = "user";
    private static final String DEFAULT_ENGINE_BOOTS = "0";
    public static String NO_AUTH_NO_PRIV = "1";
    public static String AUTH_NO_PRIV = "2";
    public static String AUTH_PRIV = "3";


    Snmp createSNMPSession(Map config) throws IOException {
        TransportMapping transportMapping = new DefaultUdpTransportMapping();
        transportMapping.listen();
        Snmp snmp = new Snmp(transportMapping);
        //if SNMP v3 then set local engine
        String version = getSnmpVersion(config);
        if(version.equalsIgnoreCase("3")){

            snmp = setSnmpSessionAttributesV3(snmp, config);

        }

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
            return createUserTarget(config);
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

    private UserTarget createUserTarget(Map config) {
        UserTarget usrTarget = new UserTarget();
        usrTarget.setVersion(SnmpConstants.version3);
        Address targetAddress = GenericAddress.parse("udp:"+ convertToString(config.get("host"),"") + "/" + convertToString(config.get("port"),""));
        usrTarget.setAddress(targetAddress);
        usrTarget.setRetries(Integer.parseInt(convertToString(config.get("retries"),DEFAULT_RETRIES)));
        usrTarget.setSecurityLevel(Integer.parseInt(convertToString(config.get("retries"),DEFAULT_SECURITY_LEVEL)));
        usrTarget.setSecurityName(new OctetString(convertToString(config.get("username"), DEFAULT_USERNAME_STRING)));
        usrTarget.setTimeout(Long.parseLong(convertToString(config.get("timeout"),DEFAULT_TIMEOUT)));

        return usrTarget;
    }


    /**
     *
     * @param snmp
     * @return snmp object, as per
     * https://github.com/Appdynamics/snmptrap-alerting-extension/blob/master/src/main/java/com/appdynamics/extensions/snmp/SNMPSender.java
     *
     */
    private Snmp setSnmpSessionAttributesV3(Snmp snmp, Map config){
        byte[] defaultEngineId = MPv3.createLocalEngineID();

        OctetString os = new OctetString(defaultEngineId);

        //USM usm = new USM(SecurityProtocols.getInstance(),os,engineProperties.getEngineBoots());

        int engineBoots = Integer.parseInt(convertToString(config.get("engineBoots"),DEFAULT_ENGINE_BOOTS));


        USM usm = new USM(SecurityProtocols.getInstance(),os,engineBoots);
        UsmTimeEntry ute = new UsmTimeEntry(os,engineBoots,getEngineTime());
        usm.getTimeTable().setLocalTime(ute);
        SecurityModels.getInstance().addSecurityModel(usm);
        snmp.setLocalEngine(defaultEngineId,engineBoots,getEngineTime());

        String securityLevel = convertToString(config.get("securityLevel"),"1");
        String userName = convertToString(config.get("username"),"user");
        String password = convertToString(config.get("password"),"password");
        String authProtocol = convertToString(config.get("authProtocol"),"MD5");
        String privateProtocol = convertToString(config.get("privProtocol"),"AES");
        String privateProtocolPassword = convertToString(config.get("privProtocolPassword"),"password");

        if(securityLevel.equals(NO_AUTH_NO_PRIV))
        {
            snmp.getUSM().addUser
                    (
                            new OctetString(userName),
                            new UsmUser
                                    (
                                            new OctetString(userName),
                                            null,
                                            null,
                                            null,
                                            null
                                    )
                    );
        }
        else if(securityLevel.equals(AUTH_NO_PRIV))
        {
            snmp.getUSM().addUser
                    (
                            new OctetString(userName),
                            new UsmUser
                                    (
                                            new OctetString(userName),
                                            (authProtocol.toUpperCase().contains("SHA")) ? AuthSHA.ID : AuthMD5.ID,
                                            new OctetString(password),
                                            null,
                                            null
                                    )
                    );
        }
        else if(securityLevel.equals(AUTH_PRIV))
        {
            OID privProtocol = PrivAES256.ID;

            String strPrivProtocol = privateProtocol;

            if (strPrivProtocol.toUpperCase().contains("3DES"))
                privProtocol = Priv3DES.ID;
            else if (strPrivProtocol.toUpperCase().contains("AES128"))
                privProtocol = PrivAES128.ID;
            else if (strPrivProtocol.toUpperCase().contains("AES192"))
                privProtocol = PrivAES192.ID;
            else if (strPrivProtocol.toUpperCase().contains("DES"))
                privProtocol = PrivDES.ID;

            snmp.getUSM().addUser
                    (
                            new OctetString(userName),
                            new UsmUser
                                    (
                                            new OctetString(userName),
                                            (authProtocol.contains("SHA")) ? AuthSHA.ID : AuthMD5.ID,
                                            new OctetString(password),
                                            privProtocol,
                                            new OctetString(privateProtocolPassword)
                                    )
                    );
        }

        return snmp;
    }


    /**
     * don't know what this does, but apparently is needed
     * @return
     */
    private int getEngineTime() {
        long currentTime = System.currentTimeMillis();
        long prevTime = 1443121086695L;//Sept 24 2015 11:159am
        long timeDiffInMs = TimeUnit.MILLISECONDS.toSeconds(currentTime - prevTime);
        return (int)(timeDiffInMs % 2147483648L);
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
