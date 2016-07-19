package com.appdynamics.extensions.siteminder;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.LoggerFactory;
import org.snmp4j.Target;
import org.snmp4j.smi.*;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SNMPWalker {

    private Target target;
    private TreeUtils treeUtils;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SNMPWalker.class);

    public SNMPWalker(Target target, TreeUtils treeUtils){
        this.target = target;
        this.treeUtils = treeUtils;
    }

    //can fetch only UnsignedInteger32(Guage32,Integer32,Integer64,etc.),IpAddress and OctetStrings
    public Map<String,Object> walk(OID[] oids) throws IOException {
        Map<String,Object> oidValueMap = Maps.<String,Object>newHashMap();
        if(oids == null){
            return oidValueMap;
        }
        logger.debug("OIDS going for a walk :: {}", Arrays.toString(oids));
        List<TreeEvent> treeEvents = treeUtils.walk(target,oids);
        for (TreeEvent event : treeEvents) {
            if(event == null){
                logger.error("Error:null event received.");
                continue;
            }
            if (event.isError()) {
                logger.error("Error:oid [{}]",event.getErrorMessage());
            }
            VariableBinding[] varBindings = event.getVariableBindings();
            if(varBindings == null || varBindings.length == 0){
                logger.error("No results returned.");
                continue;
            }
            for (VariableBinding vb : varBindings) {
                String ovid = vb.getOid().toString();
                Variable var = vb.getVariable();
                if(ovid == null){
                    logger.error("Error:null oid received from the result");
                    continue;
                }
                if(var instanceof IpAddress){
                    IpAddress addr = (IpAddress)var;
                    logger.debug("OID :: {}, SMI :: {}",ovid,addr.toString());
                    oidValueMap.put(ovid,addr.toString());
                }
                else if(var instanceof OctetString){
                    OctetString str = (OctetString)var;
                    logger.debug("OID :: {}, SMI :: {}",ovid,str.toString());
                    oidValueMap.put(ovid,str.toString());
                }
                else {
                    logger.debug("OID :: {}, SMI :: {}",ovid,var.toLong());
                    oidValueMap.put(ovid,var.toLong());
                }
            }
        }
        return oidValueMap;
    }

    public Map<String,Object> walk(Collection<String> oids) throws IOException {
        OID[] oidArray = createOIDs(oids);
        return walk(oidArray);
    }

    private OID[] createOIDs(Collection<String> stringOids){
        List<OID> oidList = Lists.newArrayList();
        for(String oid : stringOids){
            oidList.add(new OID(oid));
        }
        return oidList.toArray(new OID[oidList.size()]);
    }

}


