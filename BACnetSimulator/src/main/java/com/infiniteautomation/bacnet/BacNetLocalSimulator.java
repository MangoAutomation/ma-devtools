/**
 * Copyright (C) 2013 Infinite Automation Software. All rights reserved.
 *
 * @author Terry Packer
 */
package com.infiniteautomation.bacnet;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.enums.DayOfWeek;
import com.serotonin.bacnet4j.enums.Month;
import com.serotonin.bacnet4j.event.DeviceEventAdapter;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.exception.BACnetServiceException;
import com.serotonin.bacnet4j.npdu.Network;
import com.serotonin.bacnet4j.npdu.ip.IpNetworkBuilder;
import com.serotonin.bacnet4j.npdu.mstp.MstpNetwork;
import com.serotonin.bacnet4j.npdu.mstp.MstpNode;
import com.serotonin.bacnet4j.npdu.mstp.SlaveNode;
import com.serotonin.bacnet4j.obj.AnalogValueObject;
import com.serotonin.bacnet4j.obj.BACnetObject;
import com.serotonin.bacnet4j.obj.CalendarObject;
import com.serotonin.bacnet4j.obj.ScheduleObject;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyRequest;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.DefaultTransport;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.BACnetArray;
import com.serotonin.bacnet4j.type.constructed.CalendarEntry;
import com.serotonin.bacnet4j.type.constructed.DailySchedule;
import com.serotonin.bacnet4j.type.constructed.DateRange;
import com.serotonin.bacnet4j.type.constructed.DeviceObjectPropertyReference;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.constructed.SpecialEvent;
import com.serotonin.bacnet4j.type.constructed.TimeValue;
import com.serotonin.bacnet4j.type.constructed.WeekNDay;
import com.serotonin.bacnet4j.type.constructed.WeekNDay.WeekOfMonth;
import com.serotonin.bacnet4j.type.enumerated.EngineeringUnits;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.Date;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.bacnet4j.type.primitive.Time;
import com.serotonin.bacnet4j.type.primitive.Unsigned16;
import com.serotonin.bacnet4j.type.primitive.UnsignedInteger;
import com.serotonin.bacnet4j.util.RequestUtils;

/**
 * @author Terry Packer
 *
 */
public class BacNetLocalSimulator {
    private static final Logger log = getLogger(BacNetLocalSimulator.class);
    static LocalDevice localDevice;

    public static void main(String[] args) throws Exception {
        boolean mstp = false;

        Network network;

        if (mstp) {
            // Create an MSTP local device
            log.info("Starting MSTP Network");
            byte[] bytes = new byte[100];
            InputStream is = new ByteArrayInputStream(bytes);
            OutputStream os = new ByteArrayOutputStream();

            MstpNode node = new SlaveNode("SlaveNode", is, os, (byte) 3);
            network = new MstpNetwork(node, 20);
        } else {
            String bind = "192.168.1.8";
            int port = 47808;
            String broadcast = "255.255.255.255";

            if(args != null) {
                if(args.length > 0)
                    bind = args[0];
                if(args.length > 1)
                    port = Integer.parseInt(args[1]);
                if(args.length > 2)
                    broadcast = args[2];
            }
            log.info("Starting IP Network");
            network = new IpNetworkBuilder()
                    .withPort(port)
                    .withLocalBindAddress(bind)
                    .withLocalNetworkNumber(20)
                    .withBroadcast(broadcast, 255)
                    .build();

        }



        Transport transport = new DefaultTransport(network);
        // transport.setTimeout(15000);
        // transport.setSegTimeout(15000);
        localDevice = new LocalDevice(1234, transport);
        try {
            localDevice.initialize();
            localDevice.getEventHandler().addListener(new Listener());
            localDevice.sendGlobalBroadcast(new WhoIsRequest());

            // Create an analog Input object type
            ObjectIdentifier id = new ObjectIdentifier(ObjectType.analogInput, 0);
            BACnetObject obj = new BACnetObject(localDevice, id);

            localDevice.addObject(obj);

            // Create Accumulator Object Type
            ObjectIdentifier accumulatorId = new ObjectIdentifier(ObjectType.accumulator, 1);
            BACnetObject accumulator = new BACnetObject(localDevice, accumulatorId);
            localDevice.addObject(accumulator);


            // Create a trend Log object type
            ObjectIdentifier trendLogId = new ObjectIdentifier(ObjectType.trendLog, 1);
            BACnetObject trendLog = new BACnetObject(localDevice, trendLogId);
            localDevice.addObject(trendLog);

            // Create a Calendar
            CalendarObject co = createCalendar();
            createSchedule(co);

            Thread.sleep(200000000);
        } finally {
            localDevice.terminate();
        }
    }

    public static ScheduleObject createSchedule(CalendarObject co) throws BACnetServiceException {
        final DateRange effectivePeriod = new DateRange(Date.UNSPECIFIED, Date.UNSPECIFIED);
        final BACnetArray<DailySchedule> weeklySchedule = new BACnetArray<>( //
                new DailySchedule(new SequenceOf<>(new TimeValue(new Time(8, 0, 0, 0), new Real(10)),
                        new TimeValue(new Time(17, 0, 0, 0), new Real(11)))), //
                new DailySchedule(new SequenceOf<>(new TimeValue(new Time(8, 0, 0, 0), new Real(12)),
                        new TimeValue(new Time(17, 0, 0, 0), new Real(13)))), //
                new DailySchedule(new SequenceOf<>(new TimeValue(new Time(8, 0, 0, 0), new Real(14)),
                        new TimeValue(new Time(17, 0, 0, 0), new Real(15)))), //
                new DailySchedule(new SequenceOf<>(new TimeValue(new Time(9, 0, 0, 0), new Real(16)),
                        new TimeValue(new Time(20, 0, 0, 0), new Real(17)))), //
                new DailySchedule(new SequenceOf<>(new TimeValue(new Time(9, 0, 0, 0), new Real(18)),
                        new TimeValue(new Time(21, 30, 0, 0), new Real(19)))), //
                new DailySchedule(new SequenceOf<TimeValue>()), //
                new DailySchedule(new SequenceOf<TimeValue>()));
        final SequenceOf<SpecialEvent> exceptionSchedule = new SequenceOf<>( //
                new SpecialEvent(co.getId(),
                        new SequenceOf<>(new TimeValue(new Time(8, 0, 0, 0), new Real(20)),
                                new TimeValue(new Time(22, 0, 0, 0), new Real(21))),
                        new UnsignedInteger(10)), // Calendar
                new SpecialEvent(co.getId(),
                        new SequenceOf<>(new TimeValue(new Time(13, 0, 0, 0), new Real(22)),
                                new TimeValue(new Time(14, 0, 0, 0), new Real(23))),
                        new UnsignedInteger(7)), // Calendar
                new SpecialEvent(new CalendarEntry(new Date(-1, null, 8, DayOfWeek.WEDNESDAY)),
                        new SequenceOf<>(new TimeValue(new Time(10, 30, 0, 0), new Real(24)),
                                new TimeValue(new Time(17, 0, 0, 0), new Real(25))),
                        new UnsignedInteger(6)) // 7th is a Wednesday
                );

        final AnalogValueObject av0 = new AnalogValueObject(localDevice, 2, "av2", 98, EngineeringUnits.amperes, false)
                .supportCommandable(-2);
        final AnalogValueObject av1 = new AnalogValueObject(localDevice, 3, "av3", 99, EngineeringUnits.amperesPerMeter, false)
                .supportCommandable(-1);

        final SequenceOf<DeviceObjectPropertyReference> listOfObjectPropertyReferences = new SequenceOf<>( //
                new DeviceObjectPropertyReference(av0.getId(), PropertyIdentifier.presentValue, null, null), //
                new DeviceObjectPropertyReference(av1.getId(), PropertyIdentifier.presentValue, null, null) //
                );

        final ScheduleObject so = new ScheduleObject(localDevice, 0, "sch0", effectivePeriod, weeklySchedule, exceptionSchedule,
                new Real(8), listOfObjectPropertyReferences, 12, false);
        return so;
    }
    /**
     * Create an example calendar
     * @return
     * @throws BACnetServiceException
     */
    public static CalendarObject createCalendar() throws BACnetServiceException {
        final CalendarEntry ce = new CalendarEntry(
                new WeekNDay(Month.UNSPECIFIED, WeekOfMonth.days22to28, DayOfWeek.WEDNESDAY)); // The Wednesday during the 4th week of each month.
        final SequenceOf<CalendarEntry> dateList = new SequenceOf<>( //
                new CalendarEntry(new Date(-1, null, -1, DayOfWeek.FRIDAY)), // Every Friday.
                new CalendarEntry(
                        new DateRange(new Date(-1, Month.NOVEMBER, -1, null), new Date(-1, Month.FEBRUARY, -1, null))), // November to February
                ce);
        final CalendarObject co = new CalendarObject(localDevice, 0, "cal0", dateList);

        return co;
    }

    static class Listener extends DeviceEventAdapter {
        @Override
        public void iAmReceived(RemoteDevice d) {
            try {
                log.info("IAm received from " + d);
                log.info("Segmentation: " + d.getSegmentationSupported());

                Address a = new Address(new Unsigned16(0), new OctetString(
                        new byte[] {(byte) 0xc0, (byte) 0xa8, 0x1, 0x5, (byte) 0xba, (byte) 0xc0}), true);

                log.info("Equals: " + a.equals(d.getAddress()));
                ExtendedDeviceInformationExecutor e1 = new ExtendedDeviceInformationExecutor(d);
                e1.start();

                log.info("Done getting extended information");

                IncomingMessageExecutor e = new IncomingMessageExecutor(d);
                e.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ExtendedDeviceInformationExecutor extends Thread {

        RemoteDevice d;

        public ExtendedDeviceInformationExecutor(RemoteDevice d) {
            this.d = d;
        }

        @Override
        public void run() {
            try {
                ObjectIdentifier oid = d.getObjectIdentifier();

                // Get the device's supported services
                log.info("protocolServicesSupported");
                localDevice.send(d,
                        new ReadPropertyRequest(oid, PropertyIdentifier.protocolServicesSupported));
                //d.setServicesSupported((ServicesSupported) ack.getValue());

                log.info("objectName");
                localDevice.send(d,
                        new ReadPropertyRequest(oid, PropertyIdentifier.objectName));
                //d.setName(ack.getValue().toString());

                log.info("protocolVersion");
                localDevice.send(d,
                        new ReadPropertyRequest(oid, PropertyIdentifier.protocolVersion));
                //d.setProtocolVersion((UnsignedInteger) ack.getValue());

                // log.info("protocolRevision");
                // ack = (ReadPropertyAck) localDevice.send(d, new ReadPropertyRequest(oid,
                // PropertyIdentifier.protocolRevision));
                // d.setProtocolRevision((UnsignedInteger) ack.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class IncomingMessageExecutor extends Thread {

        RemoteDevice d;

        public IncomingMessageExecutor(RemoteDevice d) {
            this.d = d;
        }


        @Override
        public void run() {

            try {
                List<?> oids;
                oids = ((SequenceOf<?>) RequestUtils.sendReadPropertyAllowNull(localDevice, d,
                        d.getObjectIdentifier(), PropertyIdentifier.objectList)).getValues();
                log.info("Oids: {}", oids);
            } catch (BACnetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


}
