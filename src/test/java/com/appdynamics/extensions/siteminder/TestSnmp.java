/*
 * Copyright 2018. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 *
 */

package com.appdynamics.extensions.siteminder;


import com.appdynamics.extensions.siteminder.metrics.*;
import com.appdynamics.extensions.util.AggregatorFactory;
import com.appdynamics.extensions.yml.YmlReader;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.log.Log4jLogFactory;
import org.snmp4j.log.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestSnmp {

    static SNMPFactory factory = new SNMPFactory();
    /* a utility to collect component metrics. */
    static ComponentMetricsProcessor componentCollector = new ComponentMetricsProcessor();

    /* a utility to collect cluster metrics. */
    static ClusterMetricsProcessor clusterMetricsCollector = new ClusterMetricsProcessor();
    public static void main(String[] args) throws IOException {

        //LogFactory.setLogFactory(new Log4jLogFactory());
        LogFactory.setLogFactory(new org.snmp4j.log.Log4jLogFactory());
        Map config = YmlReader.readFromFileAsMap(new File(TestSnmp.class.getResource("/conf/config.yml").getPath()));
        List<Map> instances = (List<Map>) config.get("instances");
        Map instance = instances.get(0);
        Target target = factory.createTarget(instance);
        Snmp session = factory.createSNMPSession(instance);
        SNMPWalker walker = factory.createWalker(session,target,instance);
        List<String> oids = (List<String>)instance.get("test-oids");
        Map<String,Object> oidValueMap = walker.walk(oids);
        for(Map.Entry<String,Object> e : oidValueMap.entrySet()){
            System.out.println("success:oid"+e.getKey()+"::"+e.getValue());
        }
    }

//
//    public static void main(String[] args) throws IOException {
//        Snmp session = null;
//        LogFactory.setLogFactory(new org.snmp4j.log.Log4jLogFactory());
//        Map config = YmlReader.readFromFileAsMap(new File(TestSnmp.class.getResource("/conf/config.yml").getPath()));
//        List<Map> instances = (List<Map>) config.get("instances");
//        Map instance = instances.get(0);
//        try {
//            session = factory.createSNMPSession(instance);
//            Target target = factory.createTarget(instance);
//            SNMPWalker walker = factory.createWalker(session, target, instance);
//            ResourceIdentityMapper rim = new ResourceIdentityMapper(walker);
//            //creates resource map to translate machine ids to names
//            Map<String, Map> resourceIdentityMap = rim.mapResources(instance.get("resources"));
//            @SuppressWarnings("unchecked")
//            List<Map> components = (List<Map>) instance.get("metrics");
//            MetricPropertiesBuilder propertiesBuilder = new MetricPropertiesBuilder();
//            //a component can be policyServer,webAgents or likes
//            for (Map component : components) {
//                Map<String, MetricProperties> metricProps = propertiesBuilder.build(component);
//                String componentName = component.get("component").toString();
//                Map componentIdentityMap = resourceIdentityMap.get(componentName);
//                //collects  component level metrics..for eg. for policyServer
//                @SuppressWarnings("unchecked")
//                List<Metric> componentMetrics = componentCollector.collect(componentName, walker, componentIdentityMap, metricProps);
//                //collect all cluster level metrics
//                AggregatorFactory aggregatorFactory = new AggregatorFactory();
//                clusterMetricsCollector.collect(aggregatorFactory, componentMetrics);
//                //metricPrinter.reportComponentMetrics(componentMetrics);
//                //metricPrinter.reportClusterLevelMetrics(aggregatorFactory);
//
//            }
//        } catch (IOException e) {
//            System.out.println("Cannot create a SNMP UDP Session"+e);
//        } finally {
//            if (session != null) {
//                try {
//                    session.close();
//                } catch (IOException e) {
//                    System.out.println("Cannot close a SNMP UDP Session"+ e);
//                }
//            }
//
//        }
//    }

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
