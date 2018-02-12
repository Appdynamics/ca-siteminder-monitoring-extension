/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.appdynamics.extensions.yml.YmlReader;
import org.snmp4j.Snmp;
import org.snmp4j.Target;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestSnmp {

    static SNMPFactory factory = new SNMPFactory();

    public static void main(String[] args) throws IOException {
        Map config = YmlReader.readFromFileAsMap(new File("config.yml"));
        Target target = factory.createTarget(config);
        Snmp session = factory.createSNMPSession();
        SNMPWalker walker = factory.createWalker(session,target,config);
        List<String> oids = (List<String>)config.get("oids");
        Map<String,Object> oidValueMap = walker.walk(oids);
        for(Map.Entry<String,Object> e : oidValueMap.entrySet()){
            System.out.println("success:oid"+e.getKey()+"::"+e.getValue());
        }
    }

    /*
    static Set<String> VALID_DATA_TYPES = Sets.newHashSet("Integer32","Counter","Counter64","Gauge");

    public static void main(String[] args) throws IOException {
        Map config = YmlReader.readFromFileAsMap(new File("config.yml"));
        //SNMP4JSettings.setExtensibilityEnabled(true);
        //System.setProperty("org.snmp4j.smisyntaxes","mysmisyntaxes.properties");
        Address targetAddress = GenericAddress.parse("udp:"+ (String)config.get("host") + "/" + config.get("port"));
        TransportMapping transport = new DefaultUdpTransportMapping();
        transport.listen();

        CommunityTarget comTarget = new CommunityTarget();
        comTarget.setCommunity(new OctetString((String)config.get("community")));
        comTarget.setVersion(SnmpConstants.version2c);
        comTarget.setAddress(targetAddress);
        comTarget.setRetries(2);
        comTarget.setTimeout(5000);

        PDU request = new PDU();
        List<String> oids = (List<String>)config.get("oids");
        for(String oid : oids){
            request.add(new VariableBinding(new OID(oid)));
        }
        String snmpCall = config.get("snmpCall").toString();
        if(snmpCall.equals("GET")){
            makeGetCall(transport,comTarget,request);
        }
        else if(snmpCall.equals("GETNEXT")){
            makeGetNextCall(transport, comTarget, request);
        }
        else if(snmpCall.equals("GETBULK")){
            request.setMaxRepetitions(Integer.parseInt(config.get("maxRepetitions").toString()));
            request.setNonRepeaters(Integer.parseInt(config.get("nonRepeaters").toString()));
            makeBulkCall(transport,comTarget,request);
        }
        else if(snmpCall.equals("WALK")){
            makeWalkCall(transport,comTarget,oids);
        }

        transport.close();

    }

    private static void makeWalkCall(TransportMapping transport, CommunityTarget comTarget,List<String> oids) throws IOException {
        Snmp snmp = new Snmp(transport);
        for(String oid : oids){
            TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
            List<TreeEvent> events = treeUtils.getSubtree(comTarget, new OID(oid));
            System.out.println("********For OID::" + oid );

            if(events == null || events.size() == 0){
                System.out.println("No result returned.");
                System.exit(1);
            }
            // Get snmpwalk result.
            for (TreeEvent event : events) {
                if(event != null){
                    if (event.isError()) {
                        System.err.println("Error:oid [" + oid + "] " + event.getErrorMessage());
                    }

                    VariableBinding[] varBindings = event.getVariableBindings();
                    if(varBindings == null || varBindings.length == 0){
                        System.out.println("No result returned.");
                    }
                    for (VariableBinding vb : varBindings) {
                        String ovid = vb.getOid().toString();
                        Variable var = vb.getVariable();
                        if(VALID_DATA_TYPES.contains(var.getSyntaxString())){
                            System.out.println("success:"+ ovid + "::" + var.toLong());
                        }
                    }
                }
            }
        }

        snmp.close();
    }

    private static void makeGetNextCall(TransportMapping transport, CommunityTarget comTarget, PDU request) throws IOException {
        System.out.println("GET Next....");
        request.setType(PDU.GETNEXT);
        Snmp snmp = new Snmp(transport);
        ResponseEvent responseEvent = snmp.send(request, comTarget);

        PDU responsePDU=null;
        if (responseEvent != null) {
            responsePDU = responseEvent.getResponse();
            if ( responsePDU != null) {
                Vector<VariableBinding> tmpv = (Vector<VariableBinding>) responsePDU.getVariableBindings();
                if(tmpv != null) {
                    for(int k=0; k <tmpv.size();k++) {
                        VariableBinding vb = (VariableBinding) tmpv.get(k);
                        String output = null;
                        if ( vb.isException()) {
                            String errorString = vb.getVariable().getSyntaxString();
                            System.out.println("Error:"+errorString);
                        }
                        else {
                            String oid = vb.getOid().toString();
                            Variable var = vb.getVariable();
                            if(VALID_DATA_TYPES.contains(var.getSyntaxString())){
                                System.out.println("success:"+ oid + "::" + var.toLong());
                            }
                        }
                    }
                }
            }
        }
        snmp.close();
    }

    private static void makeGetCall(TransportMapping transport, CommunityTarget comTarget, PDU request) throws IOException {
        System.out.println("GET ....");
        request.setType(PDU.GET);
        Snmp snmp = new Snmp(transport);
        ResponseEvent responseEvent = snmp.send(request, comTarget);

        PDU responsePDU=null;
        if (responseEvent != null) {
            responsePDU = responseEvent.getResponse();
            if ( responsePDU != null) {
                Vector<VariableBinding> tmpv = (Vector<VariableBinding>) responsePDU.getVariableBindings();
                if(tmpv != null) {
                    for(int k=0; k <tmpv.size();k++) {
                        VariableBinding vb = (VariableBinding) tmpv.get(k);
                        String output = null;
                        if ( vb.isException()) {
                            String errorString = vb.getVariable().getSyntaxString();
                            System.out.println("Error:"+errorString);
                        }
                        else {
                            String oid = vb.getOid().toString();
                            Variable var = vb.getVariable();
                            if(VALID_DATA_TYPES.contains(var.getSyntaxString())){
                                System.out.println("success:"+ oid + "::" + var.toLong());
                            }
                        }
                    }
                }
            }
        }
        snmp.close();
    }

    private static void makeBulkCall(TransportMapping transport, CommunityTarget comTarget, PDU request) throws IOException {
        System.out.println("BULK ....");
        request.setType(PDU.GETBULK);
        Snmp snmp = new Snmp(transport);
        ResponseEvent responseEvent = snmp.send(request, comTarget);

        PDU responsePDU=null;
        if (responseEvent != null) {
            responsePDU = responseEvent.getResponse();
            if ( responsePDU != null) {
                Vector<VariableBinding> tmpv = (Vector<VariableBinding>) responsePDU.getVariableBindings();
                if(tmpv != null) {
                    for(int k=0; k <tmpv.size();k++) {
                        VariableBinding vb = (VariableBinding) tmpv.get(k);
                        String output = null;
                        if ( vb.isException()) {
                            String errorString = vb.getVariable().getSyntaxString();
                            System.out.println("Error:"+errorString);
                        }
                        else {
                            String oid = vb.getOid().toString();
                            Variable var = vb.getVariable();
                            if(VALID_DATA_TYPES.contains(var.getSyntaxString())){
                                System.out.println("success:"+ oid + "::" + var.toLong());
                            }
                        }
                    }
                }
            }
        }
        snmp.close();
    }*/
}
