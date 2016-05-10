import random
import re

csvDefinition = open("C:\\IA\\Inbound\\ThrowAway\\modbusMap.csv", "r")
outputFile = open("C:\\IA\\Outbound\\all.json", "w+")
xidsOutFile = open("C:\\IA\\Outbound\\xids-all.txt", "w+")
#rangeType = "HOLDING_REGISTER"

rangeType = "HOLDING_REGISTER"
dataType = "FOUR_BYTE_INT_SIGNED"
#dataType = "BINARY"
dataSource = "DS_155002"
newXidDict = {}

def getRandomUniqueXid() :
	loopCtrl = 0
	while loopCtrl < 1000 :
		poss = random.randint(10000, 100000)
		if poss not in newXidDict :
			newXidDict[poss] = True
			return poss
	return "GENERATION_FAILURE"


MINIMUM_LINE_LENGTH = 10 #prevent jagged access

baseHolding = """	{
         "xid":"DP_RTAC_%(xidNum)s",
         "name":"%(pointName)s",
         "enabled":true,
         "loggingType":"ALL",
         "intervalLoggingPeriodType":"MINUTES",
         "intervalLoggingType":"INSTANT",
         "purgeType":"YEARS",
         "pointLocator":{
            "range":"%(range)s",
            "modbusDataType":"%(dataType)s",
            "writeType":"NOT_SETTABLE",
            "additive":0.0,
            "bit":0,
            "charset":"ASCII",
            "multiplier":%(multiplier)s,
            "offset":%(offset)s,
            "registerCount":0,
            "slaveId":1,
            "slaveMonitor":false
         },
         "eventDetectors":[
         ],
         "plotType":"STEP",
         "unit":"%(unit)s",
         "chartColour":"",
         "chartRenderer":{
            "type":"IMAGE",
            "timePeriodType":"HOURS",
            "numberOfPeriods":2
         },
         "dataSourceXid":"%(dataSource)s",
         "defaultCacheSize":1,
         "deviceName":"RTAC - %(deviceName)s",
         "discardExtremeValues":false,
         "discardHighLimit":1.7976931348623157E308,
         "discardLowLimit":-1.7976931348623157E308,
         "intervalLoggingPeriod":15,
         "intervalLoggingSampleWindowSize":0,
         "overrideIntervalLoggingSamples":false,
         "purgeOverride":true,
         "purgePeriod":1,
         "readPermission":"AES-full-access,read-only",
         "setPermission":"AES-full-access",
         "textRenderer":{
            "type":"ANALOG",
            "useUnitAsSuffix":true,
            "unit":"%(unit)s",
            "renderedUnit":"",
            "format":"#,##0.00"
         },
         "tolerance":0.0
      }"""
	  
baseInputRegister = baseHolding

baseCoil = """{
         "xid":"DP_RTAC_%(xidNum)s",
         "name":"%(pointName)s",
         "enabled":true,
         "loggingType":"ALL",
         "intervalLoggingPeriodType":"MINUTES",
         "intervalLoggingType":"INSTANT",
         "purgeType":"YEARS",
         "pointLocator":{
            "range":"COIL_STATUS",
            "modbusDataType":"BINARY",
            "writeType":"SETTABLE",
            "additive":0.0,
            "bit":0,
            "charset":"ASCII",
            "multiplier":1.0,
            "offset":%(offset)s,
            "registerCount":0,
            "slaveId":1,
            "slaveMonitor":false
         },
         "eventDetectors":[
         ],
         "plotType":"STEP",
         "unit":"%(unit)s",
         "chartColour":"",
         "chartRenderer":{
            "type":"IMAGE",
            "timePeriodType":"HOURS",
            "numberOfPeriods":2
         },
         "dataSourceXid":"IPL_Array_RTAC",
         "defaultCacheSize":1,
         "deviceName":"%(deviceName)s",
         "discardExtremeValues":false,
         "discardHighLimit":1.7976931348623157E308,
         "discardLowLimit":-1.7976931348623157E308,
         "intervalLoggingPeriod":15,
         "intervalLoggingSampleWindowSize":0,
         "overrideIntervalLoggingSamples":false,
         "purgeOverride":true,
         "purgePeriod":1,
         "readPermission":"AES-full-access,read-only",
         "setPermission":"AES-full-access",
         "textRenderer":{
            "type":"PLAIN",
            "useUnitAsSuffix":true,
            "unit":"%(unit)s",
            "renderedUnit":"",
            "suffix":""
         },
         "tolerance":0.0
      }"""
	  
baseInputStatus = """{
         "xid":"DP_RTAC_%(xidNum)s",
         "name":"%(pointName)s",
         "enabled":true,
         "loggingType":"ALL",
         "intervalLoggingPeriodType":"MINUTES",
         "intervalLoggingType":"INSTANT",
         "purgeType":"YEARS",
         "pointLocator":{
            "range":"INPUT_STATUS",
            "modbusDataType":"BINARY",
            "writeType":"NOT_SETTABLE",
            "additive":0.0,
            "bit":0,
            "charset":"ASCII",
            "multiplier":1.0,
            "offset":%(offset)s,
            "registerCount":0,
            "slaveId":1,
            "slaveMonitor":false
         },
         "eventDetectors":[
         ],
         "plotType":"STEP",
         "unit":"",
         "chartColour":"",
         "chartRenderer":{
            "type":"IMAGE",
            "timePeriodType":"HOURS",
            "numberOfPeriods":2
         },
         "dataSourceXid":"IPL_Array_RTAC",
         "defaultCacheSize":1,
         "deviceName":"%(deviceName)s",
         "discardExtremeValues":false,
         "discardHighLimit":1.7976931348623157E308,
         "discardLowLimit":-1.7976931348623157E308,
         "intervalLoggingPeriod":15,
         "intervalLoggingSampleWindowSize":0,
         "overrideIntervalLoggingSamples":false,
         "purgeOverride":true,
         "purgePeriod":1,
         "readPermission":"AES-full-access,read-only",
         "setPermission":"AES-full-access",
         "textRenderer":{
            "type":"BINARY",
            "oneColour":null,
            "oneLabel":"%(oneLabel)s",
            "zeroColour":null,
            "zeroLabel":"%(zeroLabel)s"
         },
         "tolerance":0.0
      }"""
	  
binaryTextRenderer = """"textRenderer":{
            "type":"PLAIN",
            "useUnitAsSuffix":true,
            "unit":"%(unit)s",
            "renderedUnit":"",
         },"""

analogTextRenderer = """"textRenderer":{
            "type":"ANALOG",
            "useUnitAsSuffix":true,
            "unit":"%(unit)s",
            "renderedUnit":"",
            "format":"#,##0.00"
         },"""
	  
###
# g[0] = register address
# g[3] = pointName
# g[6] = sourceName (needs trimming?)
# g[9] = Unit (needs mapping?)
# g[10] = multiplier (clear x prefix)
# g[13] - one label
# g[14] - zero label
###

def trimSourceName(name) :
	if re.search(" VIA", name) is not None :
		return a.split(" VIA")[0]
	return name
	
def mapUnit(unit) : #installation is unitless: mapUnit(g[9])
	if unit == "" :
		return ""
	elif unit == "Volts" :
		return "V"
	elif unit == "Amps" :
		return "A"
	elif unit == "Hz" :
		return "Hz"
	elif unit == "KW" :
		return "kW"
	elif unit == "KWH" :
		return "kW*h"
	print "No unit found for: ", unit
	return ""
	
def parseMultiplier(mult) : #given to say scaled already, given to say needs scaling?
	m = re.search("([xX]?)([0-9]*[.]?[0-9]+)", mult)
	if m is not None and float(m.group(2)) != 0 :
		if len(m.group(1)) > 0:
			#		print "Parsed ", m.group(1)
			return str(1.0/int(m.group(2)))
		return m.group(2)
	return "1"
	
def convertToRange(register, dict) :
	if register < 10000 :
		dict["range"] = "COIL_STATUS"
		dict["dataType"] = "BINARY"
	elif register < 30000 :
		dict["range"] = "INPUT_STATUS"
		dict["dataType"] = "BINARY"
	elif register < 40000 :
		dict["range"] = "INPUT_REGISTER"
		dict["dataType"] = "FOUR_BYTE_INT_SIGNED"
	else :
		dict["range"] = "HOLDING_REGISTER"
		dict["dataType"] = "FOUR_BYTE_INT_SIGNED"
		
	dict["offset"] = register % 10000 #-1 #it's 1 indexed in the new doc
	
def coilWork(g, d) :
	#set the renderer, make settable
	#  This got done in the base version of the point.
	return
	
def inputStatusWork(g, d) :
	#set the renderer
	if len(g) >= 14 :
		d["oneLabel"] = g[13]
		d["zeroLabel"] = g[14]
	else :
		d["oneLabel"] = ""
		d["zeroLabel"] = ""
	
def inputRegisterWork(g, d) :
	#Fix up the text renderer and chart renderer
	#  No-op!
	return

xidNum = 0; #HR
outputFile.write("{\"dataPoints\":[")
for line in csvDefinition :
	d = {"dataSource":"Array"}
	g = line.split(",")
	if len(g) < 1 or g[0] == "" or g[3] == "" :
		continue
	if re.match("[0-9]+", g[0]) is None or re.search("SPARE", g[3]) is not None :
		continue
	modbusRegister = int(g[0])
	if len(g) < MINIMUM_LINE_LENGTH or modbusRegister is None:
		continue
	convertToRange(modbusRegister, d)
	d["multiplier"] = parseMultiplier(g[10])
	d["pointName"] = g[3].replace("\"","")
	d["deviceName"] = g[6]
	d["unit"] = mapUnit(g[9])
	d["xidNum"] = xidNum
	 #valid row, output replaced
	if modbusRegister < 10000 :
		coilWork(g, d)
		outputFile.write(baseCoil % d)
	elif modbusRegister < 30000 :
		inputStatusWork(g, d)
		outputFile.write(baseInputStatus % d)
	elif modbusRegister < 40000 :
		inputRegisterWork(g, d)
		outputFile.write(baseInputRegister % d)
	else :
		holdingWork(g, d)
		outputFile.write(baseHolding % d)
	xidsOutFile.write("DP_%(xidNum)s\n" % {"xidNum":xidNum})
	xidNum += 1
outputFile.write("]}")
