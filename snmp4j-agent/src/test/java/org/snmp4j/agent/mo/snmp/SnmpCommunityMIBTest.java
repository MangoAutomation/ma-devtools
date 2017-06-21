package org.snmp4j.agent.mo.snmp;

import org.junit.Before;
import org.junit.Test;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.USM;
import org.snmp4j.smi.OctetString;

import static org.junit.Assert.*;

/**
 * Created by fock on 08.06.2015.
 */
public class SnmpCommunityMIBTest {

  private MessageDispatcherImpl messageDispatcher;

  @Before
  public void setUp() throws Exception {
    messageDispatcher = new MessageDispatcherImpl();
    messageDispatcher.addMessageProcessingModel(new MPv3());
  }

  @Test
  public void testGetCoexistenceInfo() throws Exception {
    SnmpCommunityMIB snmpCommunityMIB = new SnmpCommunityMIB(new SnmpTargetMIB(messageDispatcher));
    assertNull(snmpCommunityMIB.getCoexistenceInfo(new OctetString("public")));
  }

  @Test
  public void testAddSnmpCommunityEntry() throws Exception {
    SnmpCommunityMIB snmpCommunityMIB = new SnmpCommunityMIB(new SnmpTargetMIB(messageDispatcher));
    snmpCommunityMIB.addSnmpCommunityEntry(new OctetString("index"), new OctetString("public2"),
        new OctetString("public1"), new OctetString(), new OctetString(), null, StorageType.readOnly);
    CoexistenceInfo[] coexistenceInfos = snmpCommunityMIB.getCoexistenceInfo(new OctetString("public2"));
    assertNotNull(coexistenceInfos);
    assertEquals(1, coexistenceInfos.length);
    assertEquals(new OctetString("public1"),coexistenceInfos[0].getSecurityName());
  }

  @Test
  public void testRemoveSnmpCommuntiyEntry() throws Exception {
    SnmpCommunityMIB snmpCommunityMIB = new SnmpCommunityMIB(new SnmpTargetMIB(messageDispatcher));
    snmpCommunityMIB.addSnmpCommunityEntry(new OctetString("index"), new OctetString("public2"),
        new OctetString("public1"), new OctetString(), new OctetString(), null, StorageType.readOnly);
    CoexistenceInfo[] coexistenceInfos = snmpCommunityMIB.getCoexistenceInfo(new OctetString("public2"));
    assertNotNull(coexistenceInfos);
    assertEquals(1, coexistenceInfos.length);
    assertEquals(new OctetString("public1"),coexistenceInfos[0].getSecurityName());
    snmpCommunityMIB.removeSnmpCommuntiyEntry(new OctetString("index"));
    coexistenceInfos = snmpCommunityMIB.getCoexistenceInfo(new OctetString("public2"));
    assertTrue(coexistenceInfos == null || coexistenceInfos.length == 0);
  }
}