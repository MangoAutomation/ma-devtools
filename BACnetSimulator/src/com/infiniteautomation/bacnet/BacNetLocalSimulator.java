/**
 * Copyright (C) 2013 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.bacnet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.event.DeviceEventAdapter;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.Network;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.npdu.mstp.MstpNetwork;
import com.serotonin.bacnet4j.npdu.mstp.MstpNode;
import com.serotonin.bacnet4j.npdu.mstp.SlaveNode;
import com.serotonin.bacnet4j.obj.BACnetObject;
import com.serotonin.bacnet4j.service.acknowledgement.ReadPropertyAck;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyRequest;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.LogRecord;
import com.serotonin.bacnet4j.type.constructed.Scale;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.constructed.ServicesSupported;
import com.serotonin.bacnet4j.type.constructed.StatusFlags;
import com.serotonin.bacnet4j.type.enumerated.EngineeringUnits;
import com.serotonin.bacnet4j.type.enumerated.EventState;
import com.serotonin.bacnet4j.type.enumerated.LoggingType;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.enumerated.Segmentation;
import com.serotonin.bacnet4j.type.primitive.CharacterString;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.bacnet4j.type.primitive.Unsigned16;
import com.serotonin.bacnet4j.type.primitive.UnsignedInteger;
import com.serotonin.bacnet4j.util.RequestUtils;

/**
 * @author Terry Packer
 *
 */
public class BacNetLocalSimulator {
    static LocalDevice localDevice;

    public static void main(String[] args) throws Exception {
    	boolean mstp = false;
    	
        Network network;
        
        if(mstp){
	        //Create an MSTP local device 
        	System.out.println("Starting MSTP Network");
	        byte[] bytes = new byte[100];
	        InputStream is = new ByteArrayInputStream(bytes);
	        OutputStream os = new ByteArrayOutputStream();
	        
	        MstpNode node = new SlaveNode(is,os,(byte)3);
	        network = new MstpNetwork(node, 20);
        }else{
        	System.out.println("Starting IP Network");
        	network = new IpNetwork("255.255.255.255", 47808,"0.0.0.0",20);
        }
        
        
        
        

        Transport transport = new Transport(network);
        //        transport.setTimeout(15000);
        //        transport.setSegTimeout(15000);
        localDevice = new LocalDevice(1234, transport);
        try {
            localDevice.initialize();
            localDevice.getEventHandler().addListener(new Listener());
            localDevice.sendGlobalBroadcast(new WhoIsRequest());
            
            //Create an analog Input object type
            ObjectIdentifier id = new ObjectIdentifier(ObjectType.analogInput, 0);
            BACnetObject obj = new BACnetObject(localDevice, id);
            //obj.setProperty(PropertyIdentifier.description, new CharacterString("Analoge Description"));
            
            obj.setProperty(PropertyIdentifier.objectName, new CharacterString("Analoge Obj Name"));
            obj.setProperty(PropertyIdentifier.presentValue, new Real(1.0f));
            //obj.setProperty(PropertyIdentifier.modelName, new CharacterString("model name"));
            obj.setProperty(PropertyIdentifier.units, new EngineeringUnits(1));
            //obj.setProperty(PropertyIdentifier.inactiveText, new CharacterString("inactive text"));
            localDevice.addObject(obj);           

            //Create Accumulator Object Type
            ObjectIdentifier accumulatorId = new ObjectIdentifier(ObjectType.accumulator, 1);
            BACnetObject accumulator = new BACnetObject(localDevice, accumulatorId);
            accumulator.setProperty(PropertyIdentifier.objectName, new CharacterString("Accumulator Obj Name"));
            accumulator.setProperty(PropertyIdentifier.scale, new Scale(new Real(1.0f)));
            accumulator.setProperty(PropertyIdentifier.maxPresValue, new UnsignedInteger(100));
            accumulator.setProperty(PropertyIdentifier.presentValue, new UnsignedInteger(1));
            localDevice.addObject(accumulator);
            
            
            //Create a trend Log object type
            ObjectIdentifier trendLogId = new ObjectIdentifier(ObjectType.trendLog, 1);
            BACnetObject trendLog = new BACnetObject(localDevice, trendLogId);
            trendLog.setProperty(PropertyIdentifier.objectName, new CharacterString("Trend Log Name"));
            trendLog.setProperty(PropertyIdentifier.enable, new com.serotonin.bacnet4j.type.primitive.Boolean(true));
            trendLog.setProperty(PropertyIdentifier.stopWhenFull, new com.serotonin.bacnet4j.type.primitive.Boolean(true));
            trendLog.setProperty(PropertyIdentifier.bufferSize, new UnsignedInteger(100));
            trendLog.setProperty(PropertyIdentifier.logBuffer, new SequenceOf<LogRecord>());
            trendLog.setProperty(PropertyIdentifier.recordCount, new UnsignedInteger(100));
            trendLog.setProperty(PropertyIdentifier.totalRecordCount, new UnsignedInteger(100));
            trendLog.setProperty(PropertyIdentifier.eventState, EventState.normal);
            trendLog.setProperty(PropertyIdentifier.loggingType, LoggingType.polled);
            trendLog.setProperty(PropertyIdentifier.statusFlags, new StatusFlags(false, false, false, true));
            localDevice.addObject(trendLog);

            
            Thread.sleep(200000000);
        }
        finally {
            localDevice.terminate();
        }
    }

    static class Listener extends DeviceEventAdapter {
        @Override
        public void iAmReceived(RemoteDevice d) {
            try {
                System.out.println("IAm received from " + d);
                System.out.println("Segmentation: " + d.getSegmentationSupported());
                d.setSegmentationSupported(Segmentation.noSegmentation);

                Address a = new Address(new Unsigned16(0), new OctetString(new byte[] { (byte) 0xc0, (byte) 0xa8, 0x1,
                        0x5, (byte) 0xba, (byte) 0xc0 }));

                System.out.println("Equals: " + a.equals(d.getAddress()));
                ExtendedDeviceInformationExecutor e1 = new ExtendedDeviceInformationExecutor(d);
                e1.start();
                
                System.out.println("Done getting extended information");

                IncomingMessageExecutor e = new IncomingMessageExecutor(d);
                e.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ExtendedDeviceInformationExecutor extends Thread{
    	
    	RemoteDevice d;
    	
    	public ExtendedDeviceInformationExecutor(RemoteDevice d){
    		this.d = d;
    	}
    	
    	@Override
    	public void run() {
    		try{
		        ObjectIdentifier oid = d.getObjectIdentifier();
		
		        // Get the device's supported services
		        System.out.println("protocolServicesSupported");
		        ReadPropertyAck ack = (ReadPropertyAck) localDevice.send(d, new ReadPropertyRequest(oid,
		                PropertyIdentifier.protocolServicesSupported));
		        d.setServicesSupported((ServicesSupported) ack.getValue());
		
		        System.out.println("objectName");
		        ack = (ReadPropertyAck) localDevice.send(d, new ReadPropertyRequest(oid, PropertyIdentifier.objectName));
		        d.setName(ack.getValue().toString());
		
		        System.out.println("protocolVersion");
		        ack = (ReadPropertyAck) localDevice.send(d, new ReadPropertyRequest(oid, PropertyIdentifier.protocolVersion));
		        d.setProtocolVersion((UnsignedInteger) ack.getValue());
		
		        //        System.out.println("protocolRevision");
		        //        ack = (ReadPropertyAck) localDevice.send(d, new ReadPropertyRequest(oid, PropertyIdentifier.protocolRevision));
		        //        d.setProtocolRevision((UnsignedInteger) ack.getValue());
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    }
    
    
    static class IncomingMessageExecutor extends Thread{
    	
    	RemoteDevice d;
    	
    	public IncomingMessageExecutor(RemoteDevice d){
    		this.d = d;
    	}
    	
    	
    	@Override
    	public void run(){

			try {
	            List oids;
				oids = ((SequenceOf) RequestUtils.sendReadPropertyAllowNull(localDevice, d,
				        d.getObjectIdentifier(), PropertyIdentifier.objectList)).getValues();
				 System.out.println(oids);
			} catch (BACnetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
    	}
    }
    
    
}
