package org.snmp4j.agent.mo.snmp;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.fail;

/**
 * Test for the {@link org.snmp4j.agent.mo.snmp.SNMPv2MIB.SysUpTimeImpl} class.
 *
 * @author Frank Fock
 */
public class SysUpTimeImplTest {

  @Test
  public void test3SysUpTimeSerializable() throws IOException {
    SysUpTime sysUpTime1 = new SNMPv2MIB.SysUpTimeImpl();
    SysUpTime sysUpTime2;

   try {
      FileOutputStream fout = new FileOutputStream("target/sysUpTimeTest");
      ObjectOutputStream oos = new ObjectOutputStream(fout);
      oos.writeObject(sysUpTime1);
      oos.close();
    } catch (NotSerializableException e) {
      fail("ERROR: Trying to write object. SysUpTime is not Serializable! " + e.getMessage());
    }

   try {
      FileInputStream fin = new FileInputStream("target/sysUpTimeTest");
      ObjectInputStream ois = new ObjectInputStream(fin);
      sysUpTime2 = (SysUpTime) ois.readObject();
      ois.close();
    } catch (ClassNotFoundException e) {
      fail("ERROR: Trying to read object. SysUpTime is not Serializable! " + e.getMessage());
    }
  }

}
