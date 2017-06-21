/*_############################################################################
  _## 
  _##  SNMP4J-Agent 2 - TestDefaultMOServer.java  
  _## 
  _##  Copyright (C) 2005-2012  Frank Fock (SNMP4J.org)
  _##  
  _##  Licensed under the Apache License, Version 2.0 (the "License");
  _##  you may not use this file except in compliance with the License.
  _##  You may obtain a copy of the License at
  _##  
  _##      http://www.apache.org/licenses/LICENSE-2.0
  _##  
  _##  Unless required by applicable law or agreed to in writing, software
  _##  distributed under the License is distributed on an "AS IS" BASIS,
  _##  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  _##  See the License for the specific language governing permissions and
  _##  limitations under the License.
  _##  
  _##########################################################################*/

package org.snmp4j.agent;

import junit.framework.*;
import org.snmp4j.agent.mo.lock.LockRequest;
import org.snmp4j.agent.mo.lock.MOLockStrategy;
import org.snmp4j.agent.request.SubRequest;
import org.snmp4j.smi.*;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.agent.mo.MOAccessImpl;

public class TestDefaultMOServer extends TestCase {

  private static final OID TEST_ENT_OID_LOW = new OID("1.3.6.1.4.1.777777.80.2.8888.1.1.1.2.1");
  private static final OID TEST_ENT_OID_UP = new OID("1.3.6.1.4.1.777777.80.2.8888.1.1.1.2.4");
  private static final OID TEST_ENT_OID_INST = new OID("1.3.6.1.4.1.777777.80.2.8888.1.1.1.2.1.0");
  private static final OID TEST_ENT_OID_INST2 = new OID("1.3.6.1.4.1.777777.80.2.8888.1.1.1.2.2.0");
  private static final OID TEST_ENT_OID_LOW2 = new OID("1.3.6.1.4.1.777777.80.2.8888.1.1.1.2.2");
  private static final OID TEST_ENT_OID_UP2 = new OID("1.3.6.1.4.1.777777.80.2.8888.1.1.1.2.5");

  private DefaultMOServer defaultMOServer = null;
  private MOContextScope tableScope1 =
            new DefaultMOContextScope(new OctetString(), TEST_ENT_OID_LOW, true, TEST_ENT_OID_UP, false);
  private MOContextScope tableScope2 =
            new DefaultMOContextScope(new OctetString("context2"), TEST_ENT_OID_LOW2, true, TEST_ENT_OID_UP2, false);
  private TestRange table = new TestRange(tableScope1);
  private TestRange table2 = new TestRange(tableScope2);


  public TestDefaultMOServer(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    defaultMOServer = new DefaultMOServer();
    defaultMOServer.register(table, new OctetString());
    defaultMOServer.register(new MOScalar<OctetString>(SnmpConstants.sysDescr,
                             MOAccessImpl.ACCESS_READ_WRITE,
                             new OctetString("contextDefault")),
                             new OctetString());
    defaultMOServer.register(new MOScalar<Integer32>(SnmpConstants.sysServices,
            MOAccessImpl.ACCESS_READ_ONLY,
            new Integer32(0)),
        new OctetString());
    defaultMOServer.register(new MOScalar<OctetString>(SnmpConstants.sysDescr,
                                                       MOAccessImpl.ACCESS_READ_WRITE,
                                                       new OctetString("context1")),
                                                       new OctetString("context1"));
    defaultMOServer.register(new MOScalar<OctetString>(SnmpConstants.sysDescr,
                                                       MOAccessImpl.ACCESS_READ_WRITE,
                                                       new OctetString("context2")),
                                                       new OctetString("context2"));
    defaultMOServer.register(table2, new OctetString("context2"));
  }

  protected void tearDown() throws Exception {
    defaultMOServer = null;
    super.tearDown();
  }

  public void testGetManagedObject() {
    OID key = SnmpConstants.sysDescr;
    OctetString context = new OctetString();
    Variable expectedReturn = new OctetString("contextDefault");
    ManagedObject actualReturn = defaultMOServer.getManagedObject(key, context);
//    System.out.println(actualReturn);
    assertNotNull(actualReturn);
    assertEquals("Result", expectedReturn,
                 ((MOScalar)actualReturn).getValue());
    context = new OctetString("context2");
    expectedReturn = new OctetString("context2");
    actualReturn = defaultMOServer.getManagedObject(key, context);
//    System.out.println(actualReturn);
    assertNotNull(actualReturn);
    assertEquals("Result", expectedReturn,
                 ((MOScalar)actualReturn).getValue());
  }

  public void testLookup() {
    OctetString context = new OctetString();
    MOQuery query = new DefaultMOQuery(
      new DefaultMOContextScope(context,
                                SnmpConstants.sysDescr, true,
                                SnmpConstants.sysName, true));
    Variable expectedReturn = new OctetString("contextDefault");
    ManagedObject actualReturn = defaultMOServer.lookup(query);
    //System.out.println(actualReturn);
    assertEquals("Result", expectedReturn,
                 ((MOScalar)actualReturn).getValue());
    context = new OctetString("context2");
    query = new DefaultMOQuery(
      new DefaultMOContextScope(context,
                                SnmpConstants.sysDescr, true,
                                SnmpConstants.sysName, true));
    actualReturn = defaultMOServer.lookup(query);
    //System.out.println(actualReturn);
    assertEquals("Result2", context, ((MOScalar)actualReturn).getValue());

    query = new DefaultMOQuery(
        new DefaultMOContextScope(new OctetString(), TEST_ENT_OID_INST, true, null, false));
    actualReturn = defaultMOServer.lookup(query);
    System.out.println(actualReturn);
    assertNotNull(actualReturn);
    assertEquals("Result3", table, actualReturn);
    query = new DefaultMOQuery(
        new DefaultMOContextScope(new OctetString("context2"), TEST_ENT_OID_INST2, true, null, false));
    actualReturn = defaultMOServer.lookup(query);
    System.out.println(actualReturn);
    assertNotNull(actualReturn);
    assertEquals("Result4", table2, actualReturn);
  }

  public void testConcurrentLookupWithLocking() throws InterruptedException {
    defaultMOServer.setLockStrategy(new MOLockStrategy() {
      @Override
      public boolean isLockNeeded(ManagedObject managedObjectLookedUp, MOQuery query) {
        return true;
      }
    });
    UpdaterThread[] updaters = new UpdaterThread[25];
    for (int i=0; i<updaters.length; i++) {
      updaters[i] = new UpdaterThread(500, 10000, 0);
      updaters[i].start();
    }
    for (UpdaterThread updater : updaters) {
      updater.join();
    }
  }

  public void _testRegister() throws DuplicateRegistrationException {
    ManagedObject mo = null;
    OctetString context = null;
    defaultMOServer.register(mo, context);

  }

  public void testUnregister() {
    OctetString context = new OctetString();
    ManagedObject mo = new MOScalar<OctetString>(SnmpConstants.sysDescr,
        MOAccessImpl.ACCESS_READ_WRITE,
        context);
    defaultMOServer.unregister(mo, null);
    assertNotNull(defaultMOServer.getManagedObject(SnmpConstants.sysDescr, context));
    ManagedObject origMO = defaultMOServer.getManagedObject(SnmpConstants.sysDescr, context);
    assertNotNull(origMO);
    assertEquals(SnmpConstants.sysDescr.trim(), origMO.getScope().getLowerBound());
    defaultMOServer.unregister(origMO, context);
    assertNull(defaultMOServer.getManagedObject(SnmpConstants.sysDescr, context));
  }


  class TestRange implements ManagedObject {
    private MOContextScope tableScope;

    public TestRange(MOContextScope scope) {
      this.tableScope = scope;
    }

    @Override
    public MOScope getScope() {
      return tableScope;
    }

    @Override
    public OID find(MOScope range) {
      OID lowerBound = range.getLowerBound();
      OID next = OID.max(((lowerBound == null) ? tableScope.getLowerBound() : lowerBound), tableScope.getLowerBound());
      if (tableScope.covers(next)) {
        return next;
      }
      return null;
    }

    @Override
    public void get(SubRequest request) {
    }

    @Override
    public boolean next(SubRequest request) {
      return false;
    }

    @Override
    public void prepare(SubRequest request) {
    }

    @Override
    public void commit(SubRequest request) {
    }

    @Override
    public void undo(SubRequest request) {
    }

    @Override
    public void cleanup(SubRequest request) {
    }

  }

  private class UpdaterThread extends Thread {
    private long waitMillis = 10;
    private long timeoutMillis = 500;
    private int updateCount = 1000;
    private boolean updateOK = false;

    public UpdaterThread(long timeoutMillis, int updateCount, long waitMillis) {
      this.timeoutMillis = timeoutMillis;
      this.updateCount = updateCount;
      this.waitMillis = waitMillis;
    }

    @Override
    public void run() {
      OctetString context = new OctetString();
      MOQuery query = new DefaultMOQuery(
          new DefaultMOContextScope(context,
              SnmpConstants.sysServices, true,
              SnmpConstants.sysServices, true));
      LockRequest lockRequest = new LockRequest(this, timeoutMillis);
      int startValue = -1;
      for (int i = 0; i < updateCount; i++) {
        ManagedObject mo = defaultMOServer.lookup(query, lockRequest);
        if (mo != null) {
          assertEquals(MOScalar.class, mo.getClass());
          assertEquals(LockRequest.LockStatus.locked, lockRequest.getLockRequestStatus());
          int currentValue = ((MOScalar) mo).getValue().toInt();
          if (startValue < 0) {
            startValue = currentValue;
          }
          //noinspection unchecked
          ((MOScalar<Integer32>) mo).setValue(new Integer32(++currentValue));
          if (waitMillis > 0) {
            try {
              sleep(waitMillis);
            } catch (InterruptedException iex) {
              // ignore
            }
          }
          int currentValueAfterWait = ((MOScalar) mo).getValue().toInt();
          boolean unlocked = defaultMOServer.unlock(lockRequest.getLockOwner(), mo);
          assertTrue(unlocked);
          assertEquals(currentValue, currentValueAfterWait);
          assertTrue(currentValueAfterWait > startValue + i);
        }
        else {
          assertEquals(LockRequest.LockStatus.lockTimedOut, lockRequest.getLockRequestStatus());
        }
      }
      updateOK = true;
    }
  }
}
