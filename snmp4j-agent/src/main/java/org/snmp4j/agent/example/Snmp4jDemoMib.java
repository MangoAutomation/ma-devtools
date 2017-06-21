 
/*_############################################################################
  _## 
  _##  SNMP4J-Agent 2 - Snmp4jDemoMib.java  
  _## 
  _##  Copyright (C) 2005-2014  Frank Fock (SNMP4J.org)
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
package org.snmp4j.agent.example;
//--AgentGen BEGIN=_BEGIN
//--AgentGen END

import org.snmp4j.smi.*;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.agent.*;
import org.snmp4j.agent.mo.*;
import org.snmp4j.agent.mo.snmp.*;
import org.snmp4j.agent.mo.snmp.smi.*;
import org.snmp4j.agent.request.*;
import org.snmp4j.log.LogFactory;
import org.snmp4j.log.LogAdapter;
import org.snmp4j.agent.mo.snmp.tc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//--AgentGen BEGIN=_IMPORT
//--AgentGen END

public class Snmp4jDemoMib 
//--AgentGen BEGIN=_EXTENDS
//--AgentGen END
implements MOGroup 
//--AgentGen BEGIN=_IMPLEMENTS
//--AgentGen END
{

  private static final LogAdapter LOGGER = 
      LogFactory.getLogger(Snmp4jDemoMib.class);

//--AgentGen BEGIN=_STATIC
  private static final char[][][] SPARSE_PATTERNS = {
    {
        { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
        { ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ' },
        { ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ' },
        { ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ' },
        { ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ' },
        { ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ' },
        { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }
    },
    {
        { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
        { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', 'x' },
        { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
        { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' }
    },
    {
        { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' }
    },
    {
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
        { 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
    },
    {
        { 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ', ' ', 'x', 'x', ' ', ' ', 'x' },
        { 'x', ' ', ' ', 'x', ' ', ' ', ' ', 'x', ' ', 'x', ' ', ' ', ' ', 'x', ' ', ' ', 'x', 'x', 'x', 'x' },
        { 'x', 'x', ' ', 'x', 'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x', 'x', 'x' },
        { 'x', 'x', ' ', 'x', 'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x', ' ', ' ', 'x', 'x', 'x', ' ', 'x', ' ' },
        { 'x', ' ', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x', ' ', 'x', ' ', 'x', 'x', ' ', 'x', ' ', ' ' },
        { ' ', ' ', 'x', ' ', ' ', ' ', 'x', ' ', 'x', ' ', ' ', ' ', 'x', 'x', 'x', ' ', 'x', ' ', ' ', 'x' },
        { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x' },
        { 'x', 'x', ' ', 'x', 'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x', ' ', 'x', ' ', 'x', ' ', ' ', 'x', 'x' },
        { ' ', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x', 'x' },
        { ' ', 'x', ' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ', 'x', ' ', 'x', 'x', ' ', 'x', 'x', 'x', ' ' },
        { 'x', ' ', ' ', 'x', 'x', ' ', ' ', 'x', ' ', 'x', 'x', ' ', ' ', 'x', 'x', 'x', 'x', 'x', ' ', 'x' },
        { 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x' },
        { ' ', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x' },
        { ' ', 'x', 'x', ' ', ' ', 'x', 'x', ' ', 'x', ' ', ' ', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x' },
        { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', ' ', 'x', ' ', 'x', 'x', ' ', ' ' },
        { 'x', ' ', ' ', 'x', 'x', ' ', ' ', 'x', ' ', 'x', 'x', ' ', ' ', 'x', 'x', 'x', 'x', ' ', 'x', ' ' },
        { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', ' ', ' ', 'x', 'x' },
        { 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x', ' ', ' ', ' ', 'x', 'x', ' ', 'x' },
        { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
        { 'x', ' ', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', ' ', 'x', ' ', 'x', ' ' }
    },
    {
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' },
        { 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' }
    }
  };
//--AgentGen END

  // Factory
  private MOFactory moFactory = 
    DefaultMOFactory.getInstance();

  // Constants 

  /**
   * OID of this MIB module for usage which can be 
   * used for its identification.
   */
  public static final OID oidSnmp4jDemoMib =
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20 });

  // Identities
  // Scalars
  public static final OID oidSnmp4jDemoScalar = 
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,1,0 });
  public static final OID oidSnmp4jDemoSparseTableType = 
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,3,0 });
  // Tables

  // Notifications
  public static final OID oidSnmp4jDemoEvent =
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,2,0,1 });   
  public static final OID oidTrapVarSnmp4jDemoEntryCol3 =
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,2,1,5 });
  public static final OID oidTrapVarSnmp4jDemoTableRowModification =
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,2,1,9 });


  // Enumerations
  public static final class Snmp4jDemoSparseTableTypeEnum {
    public static final int cross = 1;
    public static final int square = 2;
    public static final int stairsDown = 3;
    public static final int stairsUp = 4;
    public static final int rain = 5;
    public static final int random = 6;
  }

  public static final class Snmp4jDemoTableRowModificationEnum {
    public static final int created = 1;
    public static final int updated = 2;
    public static final int deleted = 3;
  }



  // TextualConventions
  private static final String TC_MODULE_SNMPV2_TC = "SNMPv2-TC";
  private static final String TC_MODULE_SNMP4J_DEMO_MIB = "SNMP4J-DEMO-MIB";
  private static final String TC_SPARSETABLECOLUMN = "SparseTableColumn";
  private static final String TC_STORAGETYPE = "StorageType";
  private static final String TC_DISPLAYSTRING = "DisplayString";
  private static final String TC_ROWSTATUS = "RowStatus";
  private static final String TC_TIMESTAMP = "TimeStamp";

  // Scalars
  private MOScalar<OctetString> snmp4jDemoScalar;
  private MOScalar<Integer32> snmp4jDemoSparseTableType;

  // Tables
  public static final OID oidSnmp4jDemoEntry = 
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,2,1 });

  // Index OID definitions
  public static final OID oidSnmp4jDemoEntryIndex1 =
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,2,1,1 });
  public static final OID oidSnmp4jDemoEntryIndex2 =
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,2,1,2 });

  // Column TC definitions for snmp4jDemoEntry:
  public static final String tcModuleSNMPv2Tc = "SNMPv2-TC";
  public static final String tcDefTimeStamp = "TimeStamp";
  public static final String tcDefStorageType = "StorageType";
  public static final String tcDefRowStatus = "RowStatus";
    
  // Column sub-identifier definitions for snmp4jDemoEntry:
  public static final int colSnmp4jDemoEntryCol1 = 3;
  public static final int colSnmp4jDemoEntryCol2 = 4;
  public static final int colSnmp4jDemoEntryCol3 = 5;
  public static final int colSnmp4jDemoEntryCol4 = 6;
  public static final int colSnmp4jDemoEntryCol5 = 7;
  public static final int colSnmp4jDemoEntryCol6 = 8;
  public static final int colSnmp4jDemoTableRowModification = 9;

  // Column index definitions for snmp4jDemoEntry:
  public static final int idxSnmp4jDemoEntryCol1 = 0;
  public static final int idxSnmp4jDemoEntryCol2 = 1;
  public static final int idxSnmp4jDemoEntryCol3 = 2;
  public static final int idxSnmp4jDemoEntryCol4 = 3;
  public static final int idxSnmp4jDemoEntryCol5 = 4;
  public static final int idxSnmp4jDemoEntryCol6 = 5;
  public static final int idxSnmp4jDemoTableRowModification = 6;

  private MOTableSubIndex[] snmp4jDemoEntryIndexes;
  private MOTableIndex snmp4jDemoEntryIndex;
  
  private MOTable<Snmp4jDemoEntryRow,
                  MOColumn,
                  MOMutableTableModel<Snmp4jDemoEntryRow>> snmp4jDemoEntry;
  private MOMutableTableModel<Snmp4jDemoEntryRow> snmp4jDemoEntryModel;
  public static final OID oidSnmp4jDemoSparseEntry = 
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,4,1 });

  // Index OID definitions
  public static final OID oidSnmp4jDemoSparseTableIndex =
    new OID(new int[] { 1,3,6,1,4,1,4976,10,1,1,20,1,4,1,1 });

  // Column TC definitions for snmp4jDemoSparseEntry:
  public static final String tcModuleSnmp4jDemoMib = "SNMP4J-DEMO-MIB";
  public static final String tcDefSparseTableColumn = "SparseTableColumn";
    
  // Column sub-identifier definitions for snmp4jDemoSparseEntry:
  public static final int colSnmp4jDemoSparseTableRowStatus = 2;
  public static final int colSnmp4jDemoSparseTableCol1 = 3;
  public static final int colSnmp4jDemoSparseTableCol2 = 4;
  public static final int colSnmp4jDemoSparseTableCol3 = 5;
  public static final int colSnmp4jDemoSparseTableCol4 = 6;
  public static final int colSnmp4jDemoSparseTableCol5 = 7;
  public static final int colSnmp4jDemoSparseTableCol6 = 8;
  public static final int colSnmp4jDemoSparseTableCol7 = 9;
  public static final int colSnmp4jDemoSparseTableCol8 = 10;
  public static final int colSnmp4jDemoSparseTableCol9 = 11;
  public static final int colSnmp4jDemoSparseTableCol10 = 12;
  public static final int colSnmp4jDemoSparseTableCol11 = 13;
  public static final int colSnmp4jDemoSparseTableCol12 = 14;
  public static final int colSnmp4jDemoSparseTableCol13 = 15;
  public static final int colSnmp4jDemoSparseTableCol14 = 16;
  public static final int colSnmp4jDemoSparseTableCol15 = 17;
  public static final int colSnmp4jDemoSparseTableCol16 = 18;
  public static final int colSnmp4jDemoSparseTableCol17 = 19;
  public static final int colSnmp4jDemoSparseTableCol18 = 20;
  public static final int colSnmp4jDemoSparseTableCol19 = 21;
  public static final int colSnmp4jDemoSparseTableCol20 = 22;
  public static final int colSnmp4jDemoSparseTableCol21 = 23;
  public static final int colSnmp4jDemoSparseTableCol22 = 24;
  public static final int colSnmp4jDemoSparseTableCol23 = 25;
  public static final int colSnmp4jDemoSparseTableCol24 = 26;
  public static final int colSnmp4jDemoSparseTableCol25 = 27;
  public static final int colSnmp4jDemoSparseTableCol26 = 28;
  public static final int colSnmp4jDemoSparseTableCol27 = 29;
  public static final int colSnmp4jDemoSparseTableCol28 = 30;
  public static final int colSnmp4jDemoSparseTableCol29 = 31;
  public static final int colSnmp4jDemoSparseTableCol30 = 32;
  public static final int colSnmp4jDemoSparseTableCol31 = 33;
  public static final int colSnmp4jDemoSparseTableCol32 = 34;
  public static final int colSnmp4jDemoSparseTableCol33 = 35;
  public static final int colSnmp4jDemoSparseTableCol34 = 36;
  public static final int colSnmp4jDemoSparseTableCol35 = 37;
  public static final int colSnmp4jDemoSparseTableCol36 = 38;
  public static final int colSnmp4jDemoSparseTableCol37 = 39;
  public static final int colSnmp4jDemoSparseTableCol38 = 40;
  public static final int colSnmp4jDemoSparseTableCol39 = 41;
  public static final int colSnmp4jDemoSparseTableCol40 = 42;
  public static final int colSnmp4jDemoSparseTableCol41 = 43;
  public static final int colSnmp4jDemoSparseTableCol42 = 44;
  public static final int colSnmp4jDemoSparseTableCol43 = 45;
  public static final int colSnmp4jDemoSparseTableCol44 = 46;
  public static final int colSnmp4jDemoSparseTableCol45 = 47;
  public static final int colSnmp4jDemoSparseTableCol46 = 48;
  public static final int colSnmp4jDemoSparseTableCol47 = 49;
  public static final int colSnmp4jDemoSparseTableCol48 = 50;
  public static final int colSnmp4jDemoSparseTableCol49 = 51;
  public static final int colSnmp4jDemoSparseTableCol50 = 52;

  // Column index definitions for snmp4jDemoSparseEntry:
  public static final int idxSnmp4jDemoSparseTableRowStatus = 0;
  public static final int idxSnmp4jDemoSparseTableCol1 = 1;
  public static final int idxSnmp4jDemoSparseTableCol2 = 2;
  public static final int idxSnmp4jDemoSparseTableCol3 = 3;
  public static final int idxSnmp4jDemoSparseTableCol4 = 4;
  public static final int idxSnmp4jDemoSparseTableCol5 = 5;
  public static final int idxSnmp4jDemoSparseTableCol6 = 6;
  public static final int idxSnmp4jDemoSparseTableCol7 = 7;
  public static final int idxSnmp4jDemoSparseTableCol8 = 8;
  public static final int idxSnmp4jDemoSparseTableCol9 = 9;
  public static final int idxSnmp4jDemoSparseTableCol10 = 10;
  public static final int idxSnmp4jDemoSparseTableCol11 = 11;
  public static final int idxSnmp4jDemoSparseTableCol12 = 12;
  public static final int idxSnmp4jDemoSparseTableCol13 = 13;
  public static final int idxSnmp4jDemoSparseTableCol14 = 14;
  public static final int idxSnmp4jDemoSparseTableCol15 = 15;
  public static final int idxSnmp4jDemoSparseTableCol16 = 16;
  public static final int idxSnmp4jDemoSparseTableCol17 = 17;
  public static final int idxSnmp4jDemoSparseTableCol18 = 18;
  public static final int idxSnmp4jDemoSparseTableCol19 = 19;
  public static final int idxSnmp4jDemoSparseTableCol20 = 20;
  public static final int idxSnmp4jDemoSparseTableCol21 = 21;
  public static final int idxSnmp4jDemoSparseTableCol22 = 22;
  public static final int idxSnmp4jDemoSparseTableCol23 = 23;
  public static final int idxSnmp4jDemoSparseTableCol24 = 24;
  public static final int idxSnmp4jDemoSparseTableCol25 = 25;
  public static final int idxSnmp4jDemoSparseTableCol26 = 26;
  public static final int idxSnmp4jDemoSparseTableCol27 = 27;
  public static final int idxSnmp4jDemoSparseTableCol28 = 28;
  public static final int idxSnmp4jDemoSparseTableCol29 = 29;
  public static final int idxSnmp4jDemoSparseTableCol30 = 30;
  public static final int idxSnmp4jDemoSparseTableCol31 = 31;
  public static final int idxSnmp4jDemoSparseTableCol32 = 32;
  public static final int idxSnmp4jDemoSparseTableCol33 = 33;
  public static final int idxSnmp4jDemoSparseTableCol34 = 34;
  public static final int idxSnmp4jDemoSparseTableCol35 = 35;
  public static final int idxSnmp4jDemoSparseTableCol36 = 36;
  public static final int idxSnmp4jDemoSparseTableCol37 = 37;
  public static final int idxSnmp4jDemoSparseTableCol38 = 38;
  public static final int idxSnmp4jDemoSparseTableCol39 = 39;
  public static final int idxSnmp4jDemoSparseTableCol40 = 40;
  public static final int idxSnmp4jDemoSparseTableCol41 = 41;
  public static final int idxSnmp4jDemoSparseTableCol42 = 42;
  public static final int idxSnmp4jDemoSparseTableCol43 = 43;
  public static final int idxSnmp4jDemoSparseTableCol44 = 44;
  public static final int idxSnmp4jDemoSparseTableCol45 = 45;
  public static final int idxSnmp4jDemoSparseTableCol46 = 46;
  public static final int idxSnmp4jDemoSparseTableCol47 = 47;
  public static final int idxSnmp4jDemoSparseTableCol48 = 48;
  public static final int idxSnmp4jDemoSparseTableCol49 = 49;
  public static final int idxSnmp4jDemoSparseTableCol50 = 50;

  private MOTableSubIndex[] snmp4jDemoSparseEntryIndexes;
  private MOTableIndex snmp4jDemoSparseEntryIndex;
  
  private MOTable<Snmp4jDemoSparseEntryRow,
                  MOColumn,
                  MOMutableTableModel<Snmp4jDemoSparseEntryRow>> snmp4jDemoSparseEntry;
  private MOMutableTableModel<Snmp4jDemoSparseEntryRow> snmp4jDemoSparseEntryModel;


//--AgentGen BEGIN=_MEMBERS
//--AgentGen END

  /**
   * Constructs a Snmp4jDemoMib instance without actually creating its
   * <code>ManagedObject</code> instances. This has to be done in a
   * sub-class constructor or after construction by calling 
   * {@link #createMO(MOFactory moFactory)}. 
   */
  protected Snmp4jDemoMib() {
//--AgentGen BEGIN=_DEFAULTCONSTRUCTOR
//--AgentGen END
  }

  /**
   * Constructs a Snmp4jDemoMib instance and actually creates its
   * <code>ManagedObject</code> instances using the supplied 
   * <code>MOFactory</code> (by calling
   * {@link #createMO(MOFactory moFactory)}).
   * @param moFactory
   *    the <code>MOFactory</code> to be used to create the
   *    managed objects for this module.
   */
  public Snmp4jDemoMib(MOFactory moFactory) {
  	this();
    createMO(moFactory);
//--AgentGen BEGIN=_FACTORYCONSTRUCTOR
    snmp4jDemoSparseTableType.setValue(new Integer32(Snmp4jDemoSparseTableTypeEnum.random));
//--AgentGen END
  }

//--AgentGen BEGIN=_CONSTRUCTORS
//--AgentGen END

  /**
   * Create the ManagedObjects defined for this MIB module
   * using the specified {@link MOFactory}.
   * @param moFactory
   *    the <code>MOFactory</code> instance to use for object 
   *    creation.
   */
  protected void createMO(MOFactory moFactory) {
    addTCsToFactory(moFactory);
    snmp4jDemoScalar = 
      new Snmp4jDemoScalar(oidSnmp4jDemoScalar, 
                           moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_WRITE));
    snmp4jDemoScalar.addMOValueValidationListener(new Snmp4jDemoScalarValidator());
    snmp4jDemoSparseTableType = 
      new Snmp4jDemoSparseTableType(oidSnmp4jDemoSparseTableType, 
                                    moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_WRITE));
    snmp4jDemoSparseTableType.addMOValueValidationListener(new Snmp4jDemoSparseTableTypeValidator());
    createSnmp4jDemoEntry(moFactory);
    createSnmp4jDemoSparseEntry(moFactory);
  }

  public MOScalar<OctetString> getSnmp4jDemoScalar() {
    return snmp4jDemoScalar;
  }
  public MOScalar<Integer32> getSnmp4jDemoSparseTableType() {
    return snmp4jDemoSparseTableType;
  }


  public MOTable<Snmp4jDemoEntryRow,MOColumn,MOMutableTableModel<Snmp4jDemoEntryRow>> getSnmp4jDemoEntry() {
    return snmp4jDemoEntry;
  }


  @SuppressWarnings(value={"unchecked"})
  private void createSnmp4jDemoEntry(MOFactory moFactory) {
    // Index definition
    snmp4jDemoEntryIndexes = 
      new MOTableSubIndex[] {
      moFactory.createSubIndex(oidSnmp4jDemoEntryIndex1, 
                               SMIConstants.SYNTAX_INTEGER, 1, 1),
      moFactory.createSubIndex(oidSnmp4jDemoEntryIndex2, 
                               SMIConstants.SYNTAX_OCTET_STRING, 1, 32)
    };

    snmp4jDemoEntryIndex = 
      moFactory.createIndex(snmp4jDemoEntryIndexes,
                            true,
                            new MOTableIndexValidator() {
      public boolean isValidIndex(OID index) {
        boolean isValidIndex = true;
     //--AgentGen BEGIN=snmp4jDemoEntry::isValidIndex
     //--AgentGen END
        return isValidIndex;
      }
    });

    // Columns
    MOColumn[] snmp4jDemoEntryColumns = new MOColumn[7];
    snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol1] = 
      new MOMutableColumn<Integer32>(colSnmp4jDemoEntryCol1,
                          SMIConstants.SYNTAX_INTEGER32,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          new Integer32(1));
    ((MOMutableColumn)snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol1]).
      addMOValueValidationListener(new Snmp4jDemoEntryCol1Validator());
    snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol2] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoEntryCol2,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          new OctetString(new byte[] {  }));
    ValueConstraint snmp4jDemoEntryCol2VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoEntryCol2VC).add(new Constraint(0L, 128L));
    ((MOMutableColumn)snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol2]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoEntryCol2VC));                                  
    ((MOMutableColumn)snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol2]).
      addMOValueValidationListener(new Snmp4jDemoEntryCol2Validator());
    snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol3] = 
      moFactory.createColumn(colSnmp4jDemoEntryCol3, 
                             SMIConstants.SYNTAX_COUNTER32,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY));
    snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol4] = 
      moFactory.createColumn(colSnmp4jDemoEntryCol4, 
                             SMIConstants.SYNTAX_TIMETICKS,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY),
                             tcModuleSNMPv2Tc,
                             tcDefTimeStamp);
    snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol5] = 
      new StorageType(colSnmp4jDemoEntryCol5,
                      moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                      new Integer32(2));
    ValueConstraint snmp4jDemoEntryCol5VC = new EnumerationConstraint(
      new int[] { 1,
                  2,
                  3,
                  4,
                  5 });
    ((MOMutableColumn)snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol5]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoEntryCol5VC));                                  
    ((MOMutableColumn)snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol5]).
      addMOValueValidationListener(new Snmp4jDemoEntryCol5Validator());
    snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol6] = 
      new RowStatus(colSnmp4jDemoEntryCol6);
    ValueConstraint snmp4jDemoEntryCol6VC = new EnumerationConstraint(
      new int[] { 1,
                  2,
                  3,
                  4,
                  5,
                  6 });
    ((MOMutableColumn)snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol6]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoEntryCol6VC));                                  
    ((MOMutableColumn)snmp4jDemoEntryColumns[idxSnmp4jDemoEntryCol6]).
      addMOValueValidationListener(new Snmp4jDemoEntryCol6Validator());
    snmp4jDemoEntryColumns[idxSnmp4jDemoTableRowModification] = 
      moFactory.createColumn(colSnmp4jDemoTableRowModification, 
                             SMIConstants.SYNTAX_INTEGER,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_NOTIFY));
    // Table model
    snmp4jDemoEntryModel = 
      (MOMutableTableModel<Snmp4jDemoEntryRow>) moFactory.createTableModel(oidSnmp4jDemoEntry,
                                 snmp4jDemoEntryIndex,
                                 snmp4jDemoEntryColumns);
    ((MOMutableTableModel<Snmp4jDemoEntryRow>)snmp4jDemoEntryModel).setRowFactory(
      new Snmp4jDemoEntryRowFactory());
    snmp4jDemoEntry = 
      moFactory.createTable(oidSnmp4jDemoEntry,
                            snmp4jDemoEntryIndex,
                            snmp4jDemoEntryColumns,
                            snmp4jDemoEntryModel);
  }

  public MOTable<Snmp4jDemoSparseEntryRow,MOColumn,MOMutableTableModel<Snmp4jDemoSparseEntryRow>> getSnmp4jDemoSparseEntry() {
    return snmp4jDemoSparseEntry;
  }


  @SuppressWarnings(value={"unchecked"})
  private void createSnmp4jDemoSparseEntry(MOFactory moFactory) {
    // Index definition
    snmp4jDemoSparseEntryIndexes = 
      new MOTableSubIndex[] {
      moFactory.createSubIndex(oidSnmp4jDemoSparseTableIndex, 
                               SMIConstants.SYNTAX_INTEGER, 1, 1)    };

    snmp4jDemoSparseEntryIndex = 
      moFactory.createIndex(snmp4jDemoSparseEntryIndexes,
                            false,
                            new MOTableIndexValidator() {
      public boolean isValidIndex(OID index) {
        boolean isValidIndex = true;
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::isValidIndex
     //--AgentGen END
        return isValidIndex;
      }
    });

    // Columns
    MOColumn[] snmp4jDemoSparseEntryColumns = new MOColumn[51];
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableRowStatus] = 
      new RowStatus(colSnmp4jDemoSparseTableRowStatus);
    ValueConstraint snmp4jDemoSparseTableRowStatusVC = new EnumerationConstraint(
      new int[] { 1,
                  2,
                  3,
                  4,
                  5,
                  6 });
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableRowStatus]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableRowStatusVC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableRowStatus]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableRowStatusValidator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol1] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol1,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol1VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol1VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol1]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol1VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol1]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol1Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol2] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol2,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol2VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol2VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol2]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol2VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol2]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol2Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol3] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol3,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol3VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol3VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol3]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol3VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol3]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol3Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol4] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol4,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol4VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol4VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol4]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol4VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol4]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol4Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol5] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol5,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol5VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol5VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol5]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol5VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol5]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol5Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol6] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol6,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol6VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol6VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol6]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol6VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol6]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol6Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol7] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol7,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol7VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol7VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol7]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol7VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol7]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol7Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol8] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol8,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol8VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol8VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol8]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol8VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol8]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol8Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol9] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol9,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol9VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol9VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol9]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol9VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol9]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol9Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol10] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol10,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol10VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol10VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol10]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol10VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol10]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol10Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol11] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol11,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol11VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol11VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol11]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol11VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol11]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol11Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol12] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol12,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol12VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol12VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol12]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol12VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol12]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol12Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol13] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol13,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol13VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol13VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol13]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol13VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol13]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol13Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol14] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol14,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol14VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol14VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol14]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol14VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol14]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol14Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol15] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol15,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol15VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol15VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol15]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol15VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol15]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol15Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol16] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol16,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol16VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol16VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol16]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol16VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol16]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol16Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol17] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol17,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol17VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol17VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol17]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol17VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol17]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol17Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol18] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol18,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol18VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol18VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol18]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol18VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol18]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol18Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol19] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol19,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol19VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol19VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol19]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol19VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol19]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol19Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol20] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol20,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol20VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol20VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol20]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol20VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol20]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol20Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol21] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol21,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol21VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol21VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol21]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol21VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol21]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol21Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol22] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol22,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol22VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol22VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol22]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol22VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol22]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol22Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol23] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol23,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol23VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol23VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol23]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol23VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol23]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol23Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol24] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol24,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol24VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol24VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol24]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol24VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol24]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol24Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol25] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol25,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol25VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol25VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol25]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol25VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol25]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol25Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol26] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol26,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol26VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol26VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol26]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol26VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol26]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol26Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol27] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol27,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol27VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol27VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol27]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol27VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol27]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol27Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol28] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol28,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol28VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol28VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol28]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol28VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol28]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol28Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol29] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol29,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol29VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol29VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol29]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol29VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol29]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol29Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol30] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol30,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol30VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol30VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol30]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol30VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol30]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol30Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol31] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol31,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol31VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol31VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol31]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol31VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol31]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol31Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol32] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol32,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol32VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol32VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol32]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol32VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol32]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol32Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol33] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol33,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol33VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol33VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol33]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol33VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol33]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol33Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol34] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol34,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol34VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol34VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol34]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol34VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol34]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol34Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol35] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol35,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol35VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol35VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol35]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol35VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol35]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol35Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol36] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol36,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol36VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol36VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol36]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol36VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol36]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol36Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol37] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol37,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol37VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol37VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol37]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol37VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol37]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol37Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol38] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol38,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol38VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol38VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol38]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol38VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol38]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol38Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol39] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol39,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol39VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol39VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol39]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol39VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol39]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol39Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol40] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol40,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol40VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol40VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol40]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol40VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol40]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol40Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol41] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol41,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol41VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol41VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol41]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol41VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol41]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol41Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol42] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol42,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol42VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol42VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol42]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol42VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol42]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol42Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol43] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol43,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol43VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol43VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol43]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol43VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol43]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol43Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol44] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol44,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol44VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol44VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol44]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol44VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol44]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol44Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol45] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol45,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol45VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol45VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol45]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol45VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol45]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol45Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol46] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol46,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol46VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol46VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol46]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol46VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol46]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol46Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol47] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol47,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol47VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol47VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol47]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol47VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol47]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol47Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol48] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol48,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol48VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol48VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol48]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol48VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol48]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol48Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol49] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol49,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol49VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol49VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol49]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol49VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol49]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol49Validator());
    snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol50] = 
      new MOMutableColumn<OctetString>(colSnmp4jDemoSparseTableCol50,
                          SMIConstants.SYNTAX_OCTET_STRING,
                          moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE),
                          (OctetString)null);
    ValueConstraint snmp4jDemoSparseTableCol50VC = new ConstraintsImpl();
    ((ConstraintsImpl)snmp4jDemoSparseTableCol50VC).add(new Constraint(0L, 10L));
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol50]).
      addMOValueValidationListener(new ValueConstraintValidator(snmp4jDemoSparseTableCol50VC));                                  
    ((MOMutableColumn)snmp4jDemoSparseEntryColumns[idxSnmp4jDemoSparseTableCol50]).
      addMOValueValidationListener(new Snmp4jDemoSparseTableCol50Validator());
    // Table model
    snmp4jDemoSparseEntryModel = 
      (MOMutableTableModel<Snmp4jDemoSparseEntryRow>) moFactory.createTableModel(oidSnmp4jDemoSparseEntry,
                                 snmp4jDemoSparseEntryIndex,
                                 snmp4jDemoSparseEntryColumns);
    ((MOMutableTableModel<Snmp4jDemoSparseEntryRow>)snmp4jDemoSparseEntryModel).setRowFactory(
      new Snmp4jDemoSparseEntryRowFactory());
    snmp4jDemoSparseEntry = 
      moFactory.createTable(oidSnmp4jDemoSparseEntry,
                            snmp4jDemoSparseEntryIndex,
                            snmp4jDemoSparseEntryColumns,
                            snmp4jDemoSparseEntryModel);
  }



  public void registerMOs(MOServer server, OctetString context) 
    throws DuplicateRegistrationException 
  {
    // Scalar Objects
    server.register(this.snmp4jDemoScalar, context);
    server.register(this.snmp4jDemoSparseTableType, context);
    server.register(this.snmp4jDemoEntry, context);
    server.register(this.snmp4jDemoSparseEntry, context);
//--AgentGen BEGIN=_registerMOs
//--AgentGen END
  }

  public void unregisterMOs(MOServer server, OctetString context) {
    // Scalar Objects
    server.unregister(this.snmp4jDemoScalar, context);
    server.unregister(this.snmp4jDemoSparseTableType, context);
    server.unregister(this.snmp4jDemoEntry, context);
    server.unregister(this.snmp4jDemoSparseEntry, context);
//--AgentGen BEGIN=_unregisterMOs
//--AgentGen END
  }

  // Notifications
  public void snmp4jDemoEvent(NotificationOriginator notificationOriginator,
                              OctetString context, VariableBinding[] vbs) {
    if (vbs.length < 2) {
      throw new IllegalArgumentException("Too few notification objects (snmp4jDemoEvent): "+
                                         vbs.length+"<2");
    }
    // snmp4jDemoEntryCol3
    if (!(vbs[0].getOid().startsWith(oidTrapVarSnmp4jDemoEntryCol3))) {
      throw new IllegalArgumentException("Variable 0 (snmp4jDemoEntryCol3)) has wrong OID: "+vbs[0].getOid()+
                                         " does not start with "+oidTrapVarSnmp4jDemoEntryCol3);
    }
    if (!snmp4jDemoEntryIndex.isValidIndex(snmp4jDemoEntry.getIndexPart(vbs[0].getOid()))) {
      throw new IllegalArgumentException("Illegal index for variable 0 (snmp4jDemoEntryCol3)) specified: "+
                                         snmp4jDemoEntry.getIndexPart(vbs[0].getOid()));
    }

    // snmp4jDemoTableRowModification
    if (!(vbs[1].getOid().startsWith(oidTrapVarSnmp4jDemoTableRowModification))) {
      throw new IllegalArgumentException("Variable 1 (snmp4jDemoTableRowModification)) has wrong OID: "+vbs[1].getOid()+
                                         " does not start with "+oidTrapVarSnmp4jDemoTableRowModification);
    }
    if (!snmp4jDemoEntryIndex.isValidIndex(snmp4jDemoEntry.getIndexPart(vbs[1].getOid()))) {
      throw new IllegalArgumentException("Illegal index for variable 1 (snmp4jDemoTableRowModification)) specified: "+
                                         snmp4jDemoEntry.getIndexPart(vbs[1].getOid()));
    }

    notificationOriginator.notify(context, oidSnmp4jDemoEvent, vbs);
  }
  
  


  // Scalars
  public class Snmp4jDemoScalar extends DisplayStringScalar<OctetString> {
    Snmp4jDemoScalar(OID oid, MOAccess access) {
      super(oid, access, new OctetString(),
            0, 
            255);
//--AgentGen BEGIN=snmp4jDemoScalar
//--AgentGen END
    }

    public int isValueOK(SubRequest request) {
      Variable newValue =
        request.getVariableBinding().getVariable();
      int valueOK = super.isValueOK(request);
      if (valueOK != SnmpConstants.SNMP_ERROR_SUCCESS) {
      	return valueOK;
      }
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 255)))) {
        valueOK = SnmpConstants.SNMP_ERROR_WRONG_LENGTH;
      }
     //--AgentGen BEGIN=snmp4jDemoScalar::isValueOK
     //--AgentGen END
      return valueOK; 
    }

    public OctetString getValue() {
     //--AgentGen BEGIN=snmp4jDemoScalar::getValue
     //--AgentGen END
      return super.getValue();    
    }

    public int setValue(OctetString newValue) {
     //--AgentGen BEGIN=snmp4jDemoScalar::setValue
     //--AgentGen END
      return super.setValue(newValue);    
    }

     //--AgentGen BEGIN=snmp4jDemoScalar::_METHODS
     //--AgentGen END

  }

  public class Snmp4jDemoSparseTableType extends EnumeratedScalar<Integer32> {
    Snmp4jDemoSparseTableType(OID oid, MOAccess access) {
      super(oid, access, new Integer32(),
            new int[] { Snmp4jDemoSparseTableTypeEnum.cross,
                        Snmp4jDemoSparseTableTypeEnum.square,
                        Snmp4jDemoSparseTableTypeEnum.stairsDown,
                        Snmp4jDemoSparseTableTypeEnum.stairsUp,
                        Snmp4jDemoSparseTableTypeEnum.rain,
                        Snmp4jDemoSparseTableTypeEnum.random });
//--AgentGen BEGIN=snmp4jDemoSparseTableType
//--AgentGen END
    }

    public int isValueOK(SubRequest request) {
      Variable newValue =
        request.getVariableBinding().getVariable();
      int valueOK = super.isValueOK(request);
      if (valueOK != SnmpConstants.SNMP_ERROR_SUCCESS) {
      	return valueOK;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableType::isValueOK
     //--AgentGen END
      return valueOK; 
    }

    public Integer32 getValue() {
     //--AgentGen BEGIN=snmp4jDemoSparseTableType::getValue
     //--AgentGen END
      return super.getValue();    
    }

    public int setValue(Integer32 newValue) {
      //--AgentGen BEGIN=snmp4jDemoSparseTableType::setValue
      char[][] pattern = SPARSE_PATTERNS[newValue.toInt()-1];
      snmp4jDemoSparseEntryModel.clear();
      Random random = new Random();
      for (int r=1; r<101; r++) {
        Variable[] values = new Variable[snmp4jDemoSparseEntry.getColumnCount()];
        values[0] = new Integer32(1);
        int colCount = snmp4jDemoSparseEntry.getColumnCount();
        for (int i = 1; i < colCount; i++) {
          char c = pattern[(r-1) % pattern.length][(i-1) % pattern[0].length];
          switch (c) {
            case 'x':
              values[i] = new OctetString(""+r+":"+i);
              break;
            case 'r':
              if (random.nextBoolean()) {
                values[i] = new OctetString(""+r+":"+i);
              }
              break;
          }
        }
        Snmp4jDemoSparseEntryRow sparseEntryRow = new Snmp4jDemoSparseEntryRow(new OID(new int[] { r }),
            values);
        snmp4jDemoSparseEntryModel.addRow(sparseEntryRow);
      }
      //--AgentGen END
      return super.setValue(newValue);    
    }

     //--AgentGen BEGIN=snmp4jDemoSparseTableType::_METHODS
     //--AgentGen END

  }


  // Value Validators
  /**
   * The <code>Snmp4jDemoScalarValidator</code> implements the value
   * validation for <code>Snmp4jDemoScalar</code>.
   */
  static class Snmp4jDemoScalarValidator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 255)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoScalar::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableTypeValidator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableType</code>.
   */
  static class Snmp4jDemoSparseTableTypeValidator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
     //--AgentGen BEGIN=snmp4jDemoSparseTableType::validate
     //--AgentGen END
    }
  }

  /**
   * The <code>Snmp4jDemoEntryCol1Validator</code> implements the value
   * validation for <code>Snmp4jDemoEntryCol1</code>.
   */
  static class Snmp4jDemoEntryCol1Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
     //--AgentGen BEGIN=snmp4jDemoEntryCol1::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoEntryCol2Validator</code> implements the value
   * validation for <code>Snmp4jDemoEntryCol2</code>.
   */
  static class Snmp4jDemoEntryCol2Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 128)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoEntryCol2::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoEntryCol5Validator</code> implements the value
   * validation for <code>Snmp4jDemoEntryCol5</code>.
   */
  static class Snmp4jDemoEntryCol5Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
     //--AgentGen BEGIN=snmp4jDemoEntryCol5::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoEntryCol6Validator</code> implements the value
   * validation for <code>Snmp4jDemoEntryCol6</code>.
   */
  static class Snmp4jDemoEntryCol6Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
     //--AgentGen BEGIN=snmp4jDemoEntryCol6::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableRowStatusValidator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableRowStatus</code>.
   */
  static class Snmp4jDemoSparseTableRowStatusValidator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
     //--AgentGen BEGIN=snmp4jDemoSparseTableRowStatus::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol1Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol1</code>.
   */
  static class Snmp4jDemoSparseTableCol1Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol1::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol2Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol2</code>.
   */
  static class Snmp4jDemoSparseTableCol2Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol2::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol3Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol3</code>.
   */
  static class Snmp4jDemoSparseTableCol3Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol3::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol4Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol4</code>.
   */
  static class Snmp4jDemoSparseTableCol4Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol4::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol5Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol5</code>.
   */
  static class Snmp4jDemoSparseTableCol5Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol5::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol6Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol6</code>.
   */
  static class Snmp4jDemoSparseTableCol6Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol6::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol7Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol7</code>.
   */
  static class Snmp4jDemoSparseTableCol7Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol7::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol8Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol8</code>.
   */
  static class Snmp4jDemoSparseTableCol8Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol8::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol9Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol9</code>.
   */
  static class Snmp4jDemoSparseTableCol9Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol9::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol10Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol10</code>.
   */
  static class Snmp4jDemoSparseTableCol10Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol10::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol11Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol11</code>.
   */
  static class Snmp4jDemoSparseTableCol11Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol11::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol12Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol12</code>.
   */
  static class Snmp4jDemoSparseTableCol12Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol12::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol13Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol13</code>.
   */
  static class Snmp4jDemoSparseTableCol13Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol13::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol14Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol14</code>.
   */
  static class Snmp4jDemoSparseTableCol14Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol14::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol15Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol15</code>.
   */
  static class Snmp4jDemoSparseTableCol15Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol15::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol16Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol16</code>.
   */
  static class Snmp4jDemoSparseTableCol16Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol16::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol17Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol17</code>.
   */
  static class Snmp4jDemoSparseTableCol17Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol17::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol18Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol18</code>.
   */
  static class Snmp4jDemoSparseTableCol18Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol18::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol19Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol19</code>.
   */
  static class Snmp4jDemoSparseTableCol19Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol19::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol20Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol20</code>.
   */
  static class Snmp4jDemoSparseTableCol20Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol20::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol21Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol21</code>.
   */
  static class Snmp4jDemoSparseTableCol21Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol21::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol22Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol22</code>.
   */
  static class Snmp4jDemoSparseTableCol22Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol22::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol23Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol23</code>.
   */
  static class Snmp4jDemoSparseTableCol23Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol23::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol24Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol24</code>.
   */
  static class Snmp4jDemoSparseTableCol24Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol24::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol25Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol25</code>.
   */
  static class Snmp4jDemoSparseTableCol25Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol25::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol26Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol26</code>.
   */
  static class Snmp4jDemoSparseTableCol26Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol26::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol27Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol27</code>.
   */
  static class Snmp4jDemoSparseTableCol27Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol27::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol28Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol28</code>.
   */
  static class Snmp4jDemoSparseTableCol28Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol28::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol29Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol29</code>.
   */
  static class Snmp4jDemoSparseTableCol29Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol29::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol30Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol30</code>.
   */
  static class Snmp4jDemoSparseTableCol30Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol30::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol31Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol31</code>.
   */
  static class Snmp4jDemoSparseTableCol31Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol31::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol32Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol32</code>.
   */
  static class Snmp4jDemoSparseTableCol32Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol32::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol33Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol33</code>.
   */
  static class Snmp4jDemoSparseTableCol33Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol33::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol34Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol34</code>.
   */
  static class Snmp4jDemoSparseTableCol34Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol34::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol35Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol35</code>.
   */
  static class Snmp4jDemoSparseTableCol35Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol35::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol36Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol36</code>.
   */
  static class Snmp4jDemoSparseTableCol36Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol36::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol37Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol37</code>.
   */
  static class Snmp4jDemoSparseTableCol37Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol37::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol38Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol38</code>.
   */
  static class Snmp4jDemoSparseTableCol38Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol38::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol39Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol39</code>.
   */
  static class Snmp4jDemoSparseTableCol39Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol39::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol40Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol40</code>.
   */
  static class Snmp4jDemoSparseTableCol40Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol40::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol41Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol41</code>.
   */
  static class Snmp4jDemoSparseTableCol41Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol41::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol42Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol42</code>.
   */
  static class Snmp4jDemoSparseTableCol42Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol42::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol43Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol43</code>.
   */
  static class Snmp4jDemoSparseTableCol43Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol43::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol44Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol44</code>.
   */
  static class Snmp4jDemoSparseTableCol44Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol44::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol45Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol45</code>.
   */
  static class Snmp4jDemoSparseTableCol45Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol45::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol46Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol46</code>.
   */
  static class Snmp4jDemoSparseTableCol46Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol46::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol47Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol47</code>.
   */
  static class Snmp4jDemoSparseTableCol47Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol47::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol48Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol48</code>.
   */
  static class Snmp4jDemoSparseTableCol48Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol48::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol49Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol49</code>.
   */
  static class Snmp4jDemoSparseTableCol49Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol49::validate
     //--AgentGen END
    }
  }
  /**
   * The <code>Snmp4jDemoSparseTableCol50Validator</code> implements the value
   * validation for <code>Snmp4jDemoSparseTableCol50</code>.
   */
  static class Snmp4jDemoSparseTableCol50Validator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 10)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=snmp4jDemoSparseTableCol50::validate
     //--AgentGen END
    }
  }

  // Rows and Factories

  public class Snmp4jDemoEntryRow extends DefaultMOMutableRow2PC {

     //--AgentGen BEGIN=snmp4jDemoEntry::RowMembers
     //--AgentGen END

    public Snmp4jDemoEntryRow(OID index, Variable[] values) {
      super(index, values);
     //--AgentGen BEGIN=snmp4jDemoEntry::RowConstructor
     //--AgentGen END
    }
    
    public Integer32 getSnmp4jDemoEntryCol1() {
     //--AgentGen BEGIN=snmp4jDemoEntry::getSnmp4jDemoEntryCol1
     //--AgentGen END
      return (Integer32) super.getValue(idxSnmp4jDemoEntryCol1);
    }  
    
    public void setSnmp4jDemoEntryCol1(Integer32 newColValue) {
     //--AgentGen BEGIN=snmp4jDemoEntry::setSnmp4jDemoEntryCol1
     //--AgentGen END
      super.setValue(idxSnmp4jDemoEntryCol1, newColValue);
    }
    
    public OctetString getSnmp4jDemoEntryCol2() {
     //--AgentGen BEGIN=snmp4jDemoEntry::getSnmp4jDemoEntryCol2
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoEntryCol2);
    }  
    
    public void setSnmp4jDemoEntryCol2(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoEntry::setSnmp4jDemoEntryCol2
     //--AgentGen END
      super.setValue(idxSnmp4jDemoEntryCol2, newColValue);
    }
    
    public Counter32 getSnmp4jDemoEntryCol3() {
     //--AgentGen BEGIN=snmp4jDemoEntry::getSnmp4jDemoEntryCol3
     //--AgentGen END
      return (Counter32) super.getValue(idxSnmp4jDemoEntryCol3);
    }  
    
    public void setSnmp4jDemoEntryCol3(Counter32 newColValue) {
     //--AgentGen BEGIN=snmp4jDemoEntry::setSnmp4jDemoEntryCol3
     //--AgentGen END
      super.setValue(idxSnmp4jDemoEntryCol3, newColValue);
    }
    
    public TimeTicks getSnmp4jDemoEntryCol4() {
     //--AgentGen BEGIN=snmp4jDemoEntry::getSnmp4jDemoEntryCol4
     //--AgentGen END
      return (TimeTicks) super.getValue(idxSnmp4jDemoEntryCol4);
    }  
    
    public void setSnmp4jDemoEntryCol4(TimeTicks newColValue) {
     //--AgentGen BEGIN=snmp4jDemoEntry::setSnmp4jDemoEntryCol4
     //--AgentGen END
      super.setValue(idxSnmp4jDemoEntryCol4, newColValue);
    }
    
    public Integer32 getSnmp4jDemoEntryCol5() {
     //--AgentGen BEGIN=snmp4jDemoEntry::getSnmp4jDemoEntryCol5
     //--AgentGen END
      return (Integer32) super.getValue(idxSnmp4jDemoEntryCol5);
    }  
    
    public void setSnmp4jDemoEntryCol5(Integer32 newColValue) {
     //--AgentGen BEGIN=snmp4jDemoEntry::setSnmp4jDemoEntryCol5
     //--AgentGen END
      super.setValue(idxSnmp4jDemoEntryCol5, newColValue);
    }
    
    public Integer32 getSnmp4jDemoEntryCol6() {
     //--AgentGen BEGIN=snmp4jDemoEntry::getSnmp4jDemoEntryCol6
     //--AgentGen END
      return (Integer32) super.getValue(idxSnmp4jDemoEntryCol6);
    }  
    
    public void setSnmp4jDemoEntryCol6(Integer32 newColValue) {
     //--AgentGen BEGIN=snmp4jDemoEntry::setSnmp4jDemoEntryCol6
     //--AgentGen END
      super.setValue(idxSnmp4jDemoEntryCol6, newColValue);
    }
    
    public Integer32 getSnmp4jDemoTableRowModification() {
     //--AgentGen BEGIN=snmp4jDemoEntry::getSnmp4jDemoTableRowModification
     //--AgentGen END
      return (Integer32) super.getValue(idxSnmp4jDemoTableRowModification);
    }  
    
    public void setSnmp4jDemoTableRowModification(Integer32 newColValue) {
     //--AgentGen BEGIN=snmp4jDemoEntry::setSnmp4jDemoTableRowModification
     //--AgentGen END
      super.setValue(idxSnmp4jDemoTableRowModification, newColValue);
    }
    
    public Variable getValue(int column) {
     //--AgentGen BEGIN=snmp4jDemoEntry::RowGetValue
     //--AgentGen END
      switch(column) {
        case idxSnmp4jDemoEntryCol1: 
        	return getSnmp4jDemoEntryCol1();
        case idxSnmp4jDemoEntryCol2: 
        	return getSnmp4jDemoEntryCol2();
        case idxSnmp4jDemoEntryCol3: 
        	return getSnmp4jDemoEntryCol3();
        case idxSnmp4jDemoEntryCol4: 
        	return getSnmp4jDemoEntryCol4();
        case idxSnmp4jDemoEntryCol5: 
        	return getSnmp4jDemoEntryCol5();
        case idxSnmp4jDemoEntryCol6: 
        	return getSnmp4jDemoEntryCol6();
        case idxSnmp4jDemoTableRowModification: 
        	return getSnmp4jDemoTableRowModification();
        default:
          return super.getValue(column);
      }
    }
    
    public void setValue(int column, Variable value) {
     //--AgentGen BEGIN=snmp4jDemoEntry::RowSetValue
     //--AgentGen END
      switch(column) {
        case idxSnmp4jDemoEntryCol1: 
        	setSnmp4jDemoEntryCol1((Integer32)value);
        	break;
        case idxSnmp4jDemoEntryCol2: 
        	setSnmp4jDemoEntryCol2((OctetString)value);
        	break;
        case idxSnmp4jDemoEntryCol3: 
        	setSnmp4jDemoEntryCol3((Counter32)value);
        	break;
        case idxSnmp4jDemoEntryCol4: 
        	setSnmp4jDemoEntryCol4((TimeTicks)value);
        	break;
        case idxSnmp4jDemoEntryCol5: 
        	setSnmp4jDemoEntryCol5((Integer32)value);
        	break;
        case idxSnmp4jDemoEntryCol6: 
        	setSnmp4jDemoEntryCol6((Integer32)value);
        	break;
        case idxSnmp4jDemoTableRowModification: 
        	setSnmp4jDemoTableRowModification((Integer32)value);
        	break;
        default:
          super.setValue(column, value);
      }
    }

     //--AgentGen BEGIN=snmp4jDemoEntry::Row
     //--AgentGen END
  }
  
  class Snmp4jDemoEntryRowFactory 
        implements MOTableRowFactory<Snmp4jDemoEntryRow>
  {
    public synchronized Snmp4jDemoEntryRow createRow(OID index, Variable[] values)
        throws UnsupportedOperationException 
    {
      Snmp4jDemoEntryRow row = 
        new Snmp4jDemoEntryRow(index, values);
     //--AgentGen BEGIN=snmp4jDemoEntry::createRow
     //--AgentGen END
      return row;
    }
    
    public synchronized void freeRow(Snmp4jDemoEntryRow row) {
     //--AgentGen BEGIN=snmp4jDemoEntry::freeRow
     //--AgentGen END
    }

     //--AgentGen BEGIN=snmp4jDemoEntry::RowFactory
     //--AgentGen END
  }

  public class Snmp4jDemoSparseEntryRow extends DefaultMOMutableRow2PC {

     //--AgentGen BEGIN=snmp4jDemoSparseEntry::RowMembers
     //--AgentGen END

    public Snmp4jDemoSparseEntryRow(OID index, Variable[] values) {
      super(index, values);
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::RowConstructor
     //--AgentGen END
    }
    
    public Integer32 getSnmp4jDemoSparseTableRowStatus() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableRowStatus
     //--AgentGen END
      return (Integer32) super.getValue(idxSnmp4jDemoSparseTableRowStatus);
    }  
    
    public void setSnmp4jDemoSparseTableRowStatus(Integer32 newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableRowStatus
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableRowStatus, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol1() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol1
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol1);
    }  
    
    public void setSnmp4jDemoSparseTableCol1(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol1
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol1, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol2() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol2
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol2);
    }  
    
    public void setSnmp4jDemoSparseTableCol2(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol2
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol2, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol3() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol3
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol3);
    }  
    
    public void setSnmp4jDemoSparseTableCol3(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol3
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol3, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol4() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol4
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol4);
    }  
    
    public void setSnmp4jDemoSparseTableCol4(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol4
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol4, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol5() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol5
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol5);
    }  
    
    public void setSnmp4jDemoSparseTableCol5(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol5
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol5, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol6() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol6
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol6);
    }  
    
    public void setSnmp4jDemoSparseTableCol6(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol6
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol6, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol7() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol7
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol7);
    }  
    
    public void setSnmp4jDemoSparseTableCol7(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol7
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol7, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol8() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol8
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol8);
    }  
    
    public void setSnmp4jDemoSparseTableCol8(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol8
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol8, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol9() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol9
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol9);
    }  
    
    public void setSnmp4jDemoSparseTableCol9(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol9
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol9, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol10() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol10
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol10);
    }  
    
    public void setSnmp4jDemoSparseTableCol10(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol10
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol10, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol11() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol11
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol11);
    }  
    
    public void setSnmp4jDemoSparseTableCol11(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol11
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol11, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol12() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol12
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol12);
    }  
    
    public void setSnmp4jDemoSparseTableCol12(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol12
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol12, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol13() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol13
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol13);
    }  
    
    public void setSnmp4jDemoSparseTableCol13(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol13
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol13, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol14() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol14
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol14);
    }  
    
    public void setSnmp4jDemoSparseTableCol14(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol14
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol14, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol15() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol15
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol15);
    }  
    
    public void setSnmp4jDemoSparseTableCol15(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol15
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol15, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol16() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol16
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol16);
    }  
    
    public void setSnmp4jDemoSparseTableCol16(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol16
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol16, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol17() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol17
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol17);
    }  
    
    public void setSnmp4jDemoSparseTableCol17(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol17
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol17, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol18() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol18
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol18);
    }  
    
    public void setSnmp4jDemoSparseTableCol18(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol18
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol18, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol19() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol19
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol19);
    }  
    
    public void setSnmp4jDemoSparseTableCol19(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol19
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol19, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol20() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol20
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol20);
    }  
    
    public void setSnmp4jDemoSparseTableCol20(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol20
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol20, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol21() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol21
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol21);
    }  
    
    public void setSnmp4jDemoSparseTableCol21(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol21
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol21, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol22() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol22
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol22);
    }  
    
    public void setSnmp4jDemoSparseTableCol22(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol22
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol22, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol23() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol23
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol23);
    }  
    
    public void setSnmp4jDemoSparseTableCol23(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol23
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol23, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol24() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol24
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol24);
    }  
    
    public void setSnmp4jDemoSparseTableCol24(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol24
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol24, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol25() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol25
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol25);
    }  
    
    public void setSnmp4jDemoSparseTableCol25(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol25
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol25, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol26() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol26
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol26);
    }  
    
    public void setSnmp4jDemoSparseTableCol26(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol26
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol26, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol27() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol27
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol27);
    }  
    
    public void setSnmp4jDemoSparseTableCol27(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol27
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol27, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol28() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol28
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol28);
    }  
    
    public void setSnmp4jDemoSparseTableCol28(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol28
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol28, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol29() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol29
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol29);
    }  
    
    public void setSnmp4jDemoSparseTableCol29(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol29
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol29, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol30() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol30
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol30);
    }  
    
    public void setSnmp4jDemoSparseTableCol30(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol30
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol30, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol31() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol31
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol31);
    }  
    
    public void setSnmp4jDemoSparseTableCol31(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol31
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol31, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol32() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol32
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol32);
    }  
    
    public void setSnmp4jDemoSparseTableCol32(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol32
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol32, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol33() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol33
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol33);
    }  
    
    public void setSnmp4jDemoSparseTableCol33(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol33
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol33, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol34() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol34
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol34);
    }  
    
    public void setSnmp4jDemoSparseTableCol34(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol34
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol34, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol35() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol35
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol35);
    }  
    
    public void setSnmp4jDemoSparseTableCol35(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol35
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol35, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol36() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol36
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol36);
    }  
    
    public void setSnmp4jDemoSparseTableCol36(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol36
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol36, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol37() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol37
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol37);
    }  
    
    public void setSnmp4jDemoSparseTableCol37(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol37
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol37, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol38() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol38
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol38);
    }  
    
    public void setSnmp4jDemoSparseTableCol38(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol38
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol38, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol39() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol39
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol39);
    }  
    
    public void setSnmp4jDemoSparseTableCol39(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol39
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol39, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol40() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol40
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol40);
    }  
    
    public void setSnmp4jDemoSparseTableCol40(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol40
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol40, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol41() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol41
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol41);
    }  
    
    public void setSnmp4jDemoSparseTableCol41(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol41
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol41, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol42() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol42
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol42);
    }  
    
    public void setSnmp4jDemoSparseTableCol42(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol42
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol42, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol43() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol43
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol43);
    }  
    
    public void setSnmp4jDemoSparseTableCol43(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol43
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol43, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol44() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol44
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol44);
    }  
    
    public void setSnmp4jDemoSparseTableCol44(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol44
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol44, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol45() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol45
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol45);
    }  
    
    public void setSnmp4jDemoSparseTableCol45(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol45
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol45, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol46() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol46
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol46);
    }  
    
    public void setSnmp4jDemoSparseTableCol46(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol46
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol46, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol47() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol47
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol47);
    }  
    
    public void setSnmp4jDemoSparseTableCol47(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol47
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol47, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol48() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol48
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol48);
    }  
    
    public void setSnmp4jDemoSparseTableCol48(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol48
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol48, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol49() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol49
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol49);
    }  
    
    public void setSnmp4jDemoSparseTableCol49(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol49
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol49, newColValue);
    }
    
    public OctetString getSnmp4jDemoSparseTableCol50() {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::getSnmp4jDemoSparseTableCol50
     //--AgentGen END
      return (OctetString) super.getValue(idxSnmp4jDemoSparseTableCol50);
    }  
    
    public void setSnmp4jDemoSparseTableCol50(OctetString newColValue) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::setSnmp4jDemoSparseTableCol50
     //--AgentGen END
      super.setValue(idxSnmp4jDemoSparseTableCol50, newColValue);
    }
    
    public Variable getValue(int column) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::RowGetValue
     //--AgentGen END
      switch(column) {
        case idxSnmp4jDemoSparseTableRowStatus: 
        	return getSnmp4jDemoSparseTableRowStatus();
        case idxSnmp4jDemoSparseTableCol1: 
        	return getSnmp4jDemoSparseTableCol1();
        case idxSnmp4jDemoSparseTableCol2: 
        	return getSnmp4jDemoSparseTableCol2();
        case idxSnmp4jDemoSparseTableCol3: 
        	return getSnmp4jDemoSparseTableCol3();
        case idxSnmp4jDemoSparseTableCol4: 
        	return getSnmp4jDemoSparseTableCol4();
        case idxSnmp4jDemoSparseTableCol5: 
        	return getSnmp4jDemoSparseTableCol5();
        case idxSnmp4jDemoSparseTableCol6: 
        	return getSnmp4jDemoSparseTableCol6();
        case idxSnmp4jDemoSparseTableCol7: 
        	return getSnmp4jDemoSparseTableCol7();
        case idxSnmp4jDemoSparseTableCol8: 
        	return getSnmp4jDemoSparseTableCol8();
        case idxSnmp4jDemoSparseTableCol9: 
        	return getSnmp4jDemoSparseTableCol9();
        case idxSnmp4jDemoSparseTableCol10: 
        	return getSnmp4jDemoSparseTableCol10();
        case idxSnmp4jDemoSparseTableCol11: 
        	return getSnmp4jDemoSparseTableCol11();
        case idxSnmp4jDemoSparseTableCol12: 
        	return getSnmp4jDemoSparseTableCol12();
        case idxSnmp4jDemoSparseTableCol13: 
        	return getSnmp4jDemoSparseTableCol13();
        case idxSnmp4jDemoSparseTableCol14: 
        	return getSnmp4jDemoSparseTableCol14();
        case idxSnmp4jDemoSparseTableCol15: 
        	return getSnmp4jDemoSparseTableCol15();
        case idxSnmp4jDemoSparseTableCol16: 
        	return getSnmp4jDemoSparseTableCol16();
        case idxSnmp4jDemoSparseTableCol17: 
        	return getSnmp4jDemoSparseTableCol17();
        case idxSnmp4jDemoSparseTableCol18: 
        	return getSnmp4jDemoSparseTableCol18();
        case idxSnmp4jDemoSparseTableCol19: 
        	return getSnmp4jDemoSparseTableCol19();
        case idxSnmp4jDemoSparseTableCol20: 
        	return getSnmp4jDemoSparseTableCol20();
        case idxSnmp4jDemoSparseTableCol21: 
        	return getSnmp4jDemoSparseTableCol21();
        case idxSnmp4jDemoSparseTableCol22: 
        	return getSnmp4jDemoSparseTableCol22();
        case idxSnmp4jDemoSparseTableCol23: 
        	return getSnmp4jDemoSparseTableCol23();
        case idxSnmp4jDemoSparseTableCol24: 
        	return getSnmp4jDemoSparseTableCol24();
        case idxSnmp4jDemoSparseTableCol25: 
        	return getSnmp4jDemoSparseTableCol25();
        case idxSnmp4jDemoSparseTableCol26: 
        	return getSnmp4jDemoSparseTableCol26();
        case idxSnmp4jDemoSparseTableCol27: 
        	return getSnmp4jDemoSparseTableCol27();
        case idxSnmp4jDemoSparseTableCol28: 
        	return getSnmp4jDemoSparseTableCol28();
        case idxSnmp4jDemoSparseTableCol29: 
        	return getSnmp4jDemoSparseTableCol29();
        case idxSnmp4jDemoSparseTableCol30: 
        	return getSnmp4jDemoSparseTableCol30();
        case idxSnmp4jDemoSparseTableCol31: 
        	return getSnmp4jDemoSparseTableCol31();
        case idxSnmp4jDemoSparseTableCol32: 
        	return getSnmp4jDemoSparseTableCol32();
        case idxSnmp4jDemoSparseTableCol33: 
        	return getSnmp4jDemoSparseTableCol33();
        case idxSnmp4jDemoSparseTableCol34: 
        	return getSnmp4jDemoSparseTableCol34();
        case idxSnmp4jDemoSparseTableCol35: 
        	return getSnmp4jDemoSparseTableCol35();
        case idxSnmp4jDemoSparseTableCol36: 
        	return getSnmp4jDemoSparseTableCol36();
        case idxSnmp4jDemoSparseTableCol37: 
        	return getSnmp4jDemoSparseTableCol37();
        case idxSnmp4jDemoSparseTableCol38: 
        	return getSnmp4jDemoSparseTableCol38();
        case idxSnmp4jDemoSparseTableCol39: 
        	return getSnmp4jDemoSparseTableCol39();
        case idxSnmp4jDemoSparseTableCol40: 
        	return getSnmp4jDemoSparseTableCol40();
        case idxSnmp4jDemoSparseTableCol41: 
        	return getSnmp4jDemoSparseTableCol41();
        case idxSnmp4jDemoSparseTableCol42: 
        	return getSnmp4jDemoSparseTableCol42();
        case idxSnmp4jDemoSparseTableCol43: 
        	return getSnmp4jDemoSparseTableCol43();
        case idxSnmp4jDemoSparseTableCol44: 
        	return getSnmp4jDemoSparseTableCol44();
        case idxSnmp4jDemoSparseTableCol45: 
        	return getSnmp4jDemoSparseTableCol45();
        case idxSnmp4jDemoSparseTableCol46: 
        	return getSnmp4jDemoSparseTableCol46();
        case idxSnmp4jDemoSparseTableCol47: 
        	return getSnmp4jDemoSparseTableCol47();
        case idxSnmp4jDemoSparseTableCol48: 
        	return getSnmp4jDemoSparseTableCol48();
        case idxSnmp4jDemoSparseTableCol49: 
        	return getSnmp4jDemoSparseTableCol49();
        case idxSnmp4jDemoSparseTableCol50: 
        	return getSnmp4jDemoSparseTableCol50();
        default:
          return super.getValue(column);
      }
    }
    
    public void setValue(int column, Variable value) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::RowSetValue
     //--AgentGen END
      switch(column) {
        case idxSnmp4jDemoSparseTableRowStatus: 
        	setSnmp4jDemoSparseTableRowStatus((Integer32)value);
        	break;
        case idxSnmp4jDemoSparseTableCol1: 
        	setSnmp4jDemoSparseTableCol1((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol2: 
        	setSnmp4jDemoSparseTableCol2((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol3: 
        	setSnmp4jDemoSparseTableCol3((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol4: 
        	setSnmp4jDemoSparseTableCol4((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol5: 
        	setSnmp4jDemoSparseTableCol5((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol6: 
        	setSnmp4jDemoSparseTableCol6((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol7: 
        	setSnmp4jDemoSparseTableCol7((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol8: 
        	setSnmp4jDemoSparseTableCol8((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol9: 
        	setSnmp4jDemoSparseTableCol9((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol10: 
        	setSnmp4jDemoSparseTableCol10((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol11: 
        	setSnmp4jDemoSparseTableCol11((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol12: 
        	setSnmp4jDemoSparseTableCol12((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol13: 
        	setSnmp4jDemoSparseTableCol13((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol14: 
        	setSnmp4jDemoSparseTableCol14((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol15: 
        	setSnmp4jDemoSparseTableCol15((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol16: 
        	setSnmp4jDemoSparseTableCol16((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol17: 
        	setSnmp4jDemoSparseTableCol17((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol18: 
        	setSnmp4jDemoSparseTableCol18((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol19: 
        	setSnmp4jDemoSparseTableCol19((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol20: 
        	setSnmp4jDemoSparseTableCol20((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol21: 
        	setSnmp4jDemoSparseTableCol21((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol22: 
        	setSnmp4jDemoSparseTableCol22((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol23: 
        	setSnmp4jDemoSparseTableCol23((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol24: 
        	setSnmp4jDemoSparseTableCol24((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol25: 
        	setSnmp4jDemoSparseTableCol25((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol26: 
        	setSnmp4jDemoSparseTableCol26((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol27: 
        	setSnmp4jDemoSparseTableCol27((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol28: 
        	setSnmp4jDemoSparseTableCol28((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol29: 
        	setSnmp4jDemoSparseTableCol29((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol30: 
        	setSnmp4jDemoSparseTableCol30((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol31: 
        	setSnmp4jDemoSparseTableCol31((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol32: 
        	setSnmp4jDemoSparseTableCol32((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol33: 
        	setSnmp4jDemoSparseTableCol33((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol34: 
        	setSnmp4jDemoSparseTableCol34((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol35: 
        	setSnmp4jDemoSparseTableCol35((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol36: 
        	setSnmp4jDemoSparseTableCol36((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol37: 
        	setSnmp4jDemoSparseTableCol37((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol38: 
        	setSnmp4jDemoSparseTableCol38((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol39: 
        	setSnmp4jDemoSparseTableCol39((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol40: 
        	setSnmp4jDemoSparseTableCol40((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol41: 
        	setSnmp4jDemoSparseTableCol41((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol42: 
        	setSnmp4jDemoSparseTableCol42((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol43: 
        	setSnmp4jDemoSparseTableCol43((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol44: 
        	setSnmp4jDemoSparseTableCol44((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol45: 
        	setSnmp4jDemoSparseTableCol45((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol46: 
        	setSnmp4jDemoSparseTableCol46((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol47: 
        	setSnmp4jDemoSparseTableCol47((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol48: 
        	setSnmp4jDemoSparseTableCol48((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol49: 
        	setSnmp4jDemoSparseTableCol49((OctetString)value);
        	break;
        case idxSnmp4jDemoSparseTableCol50: 
        	setSnmp4jDemoSparseTableCol50((OctetString)value);
        	break;
        default:
          super.setValue(column, value);
      }
    }

     //--AgentGen BEGIN=snmp4jDemoSparseEntry::Row
     //--AgentGen END
  }
  
  class Snmp4jDemoSparseEntryRowFactory 
        implements MOTableRowFactory<Snmp4jDemoSparseEntryRow>
  {
    public synchronized Snmp4jDemoSparseEntryRow createRow(OID index, Variable[] values)
        throws UnsupportedOperationException 
    {
      Snmp4jDemoSparseEntryRow row = 
        new Snmp4jDemoSparseEntryRow(index, values);
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::createRow
     //--AgentGen END
      return row;
    }
    
    public synchronized void freeRow(Snmp4jDemoSparseEntryRow row) {
     //--AgentGen BEGIN=snmp4jDemoSparseEntry::freeRow
     //--AgentGen END
    }

     //--AgentGen BEGIN=snmp4jDemoSparseEntry::RowFactory
     //--AgentGen END
  }


//--AgentGen BEGIN=_METHODS
//--AgentGen END

  // Textual Definitions of MIB module Snmp4jDemoMib
  protected void addTCsToFactory(MOFactory moFactory) {
   moFactory.addTextualConvention(new SparseTableColumn()); 
  }


  public class SparseTableColumn implements TextualConvention {
  	
    public SparseTableColumn() {
    }

    public String getModuleName() {
      return TC_MODULE_SNMP4J_DEMO_MIB;
    }
  	
    public String getName() {
      return TC_SPARSETABLECOLUMN;
    }
    
    public Variable createInitialValue() {
    	Variable v = new OctetString();
      if (v instanceof AssignableFromLong) {
      	((AssignableFromLong)v).setValue(0L);
      }
    	// further modify value to comply with TC constraints here:
     //--AgentGen BEGIN=SparseTableColumn::createInitialValue
     //--AgentGen END
	    return v;
    }
  	
    public MOScalar createScalar(OID oid, MOAccess access, Variable value) {
      MOScalar scalar = moFactory.createScalar(oid, access, value);
      ValueConstraint vc = new ConstraintsImpl();
      ((ConstraintsImpl)vc).add(new Constraint(0L, 10L));
      scalar.addMOValueValidationListener(new ValueConstraintValidator(vc));                                  
     //--AgentGen BEGIN=SparseTableColumn::createScalar
     //--AgentGen END
      return scalar;
    }
  	
    public MOColumn createColumn(int columnID, int syntax, MOAccess access,
                                 Variable defaultValue, boolean mutableInService) {
      MOColumn col = moFactory.createColumn(columnID, syntax, access, 
                                            defaultValue, mutableInService);
      if (col instanceof MOMutableColumn) {
        MOMutableColumn mcol = (MOMutableColumn)col;
        ValueConstraint vc = new ConstraintsImpl();
        ((ConstraintsImpl)vc).add(new Constraint(0L, 10L));
        mcol.addMOValueValidationListener(new ValueConstraintValidator(vc));                                  
      }
     //--AgentGen BEGIN=SparseTableColumn::createColumn
     //--AgentGen END
      return col;      
    }
  }


//--AgentGen BEGIN=_TC_CLASSES_IMPORTED_MODULES_BEGIN
//--AgentGen END

  // Textual Definitions of other MIB modules
  public void addImportedTCsToFactory(MOFactory moFactory) {
  }


//--AgentGen BEGIN=_TC_CLASSES_IMPORTED_MODULES_END
//--AgentGen END

//--AgentGen BEGIN=_CLASSES
//--AgentGen END

//--AgentGen BEGIN=_END
//--AgentGen END
}


