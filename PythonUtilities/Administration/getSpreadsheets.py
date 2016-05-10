import json
import re
import types

directoryPrefix = "C:\\IA\\Inbound\\ThrowAway\\22\\"
corePrefix = "Array"

DELIMITER = "`"
delimiterDetected = False

configFile = open(directoryPrefix + corePrefix + ".json")
config = json.load(configFile)
configFile.close()

def getTupledProperty(t, item) :
	for link in t :
		if link not in item :
			return ""
		if type(item) == types.DictType :
			item = item[link]
		else :
			return ""
	return str(item)

def writeHeader(header, file) :
	row = ""
	for col in header :
		if col.find(DELIMITER) != -1 :
			delimiterDetected = True
		row = row + col + DELIMITER
	row = re.sub(DELIMITER+"$", "\n", row)
	file.write(row)

def writeItem(header, map, item, file) :
	row = ""
	for col in header :
		if col in map and (map[col] in item or type(map[col]) == types.FunctionType):
			row = row + (str(item[map[col]]) if type(map[col]) == type('') else map[col](item))  + DELIMITER
		elif col in map and type(map[col]) == types.TupleType :
			row = row + getTupledProperty(map[col], item) + DELIMITER
		else :
			row = row + DELIMITER
#			print "Key failure for: " + col + (" in " + item["name"] if "name" in item else "")
	if len(header) != row.count(DELIMITER) :
		delimiterDetected = True
		print "Extra delimiter in " + item["xid"], len(header), "  ", row.count(DELIMITER)
	row = re.sub(DELIMITER+"$", "\n", row)
	file.write(row)

#Data source section...
def getRefreshRate(ds) :
	if "updatePeriods" in ds and "updatePeriodType" in ds :
		return str(ds["updatePeriods"]) + " " + ds["updatePeriodType"]
	return ""
	
def getTcpTimeout(ds) :
	if "timeout" in ds and "retries" in ds :
		return str(ds["timeout"]) + "x" + str(ds["retries"])
	return ""

dsHeaders = ["Data Source Identifier", "Name", "IP Address", "Port", "Refresh Rate", "TCP Timeout",
"Connection", "Protocol"]
dsPropertyMap = {"Data Source Identifier":"xid", "Name":"name", "IP Address":"host",
"Port":"port", "Refresh Rate":getRefreshRate, "TCP Timeout":getTcpTimeout,
"Connection":"transportType", "Protocol":"type"}
	
dsOutFile = open(directoryPrefix + corePrefix + "dsOut.csv", "w+")
dsXidMap = {}
writeHeader(dsHeaders, dsOutFile)
for ds in config["dataSources"] :
	dsXidMap[ds["xid"]] = ds
	writeItem(dsHeaders, dsPropertyMap, ds, dsOutFile)
dsOutFile.close()

#Data point section
def getDpRendering(dp) :
	if dp["textRenderer"]["type"] == "BINARY" :
		return ("0: "+dp["textRenderer"]["zeroLabel"]+" 1:"+dp["textRenderer"]["oneLabel"]).replace("\r", "").replace("\n", "")
	elif dp["textRenderer"]["type"] == "MULTISTATE" :
		ans = ""
		for val in dp["textRenderer"]["multistateValues"] :
			ans += str(val["key"]) + ": " + val["text"] + " -- "
		ans = re.sub(" -- $", "", ans)
		return ans.replace("\r", "").replace("\n", "")
	return ""
	
def getDpDescription(dp) :
	return ""

modbusHeaders = ["Point Identifier", "Name", "Data Source XID", "Slave ID", "Register Offset", "Range", "Data Type", 
	"Bit Offset", "Scale Factor", "Offset Factor", "Format String", "Rendering"]
modbusPropertyMap = {"Point Identifier":"xid", "Name":"name", "Data Source XID":"dataSourceXid", "Slave ID":("pointLocator", "slaveId"), "Register Offset":("pointLocator", "offset"), "Range":("pointLocator", "range"), 
"Data Type":("pointLocator", "modbusDataType"), "Bit Offset":("pointLocator", "bit"), "Scale Factor":("pointLocator", "multiplier"), "Offset Factor":("pointLocator", "additive"), "Format String":("textRenderer", "format"), "Rendering":getDpRendering}

metaHeaders = ["Point Identifier", "Name", "Data Source XID", "Data Type", "Format String", "Rendering", "Description"]
metaPropertyMap = {"Point Identifier":"xid", "Name":"name", "Data Source XID":"dataSourceXid", "Data Type":("pointLocator","dataType"), 
"Format String":("textRenderer", "format"), "Rendering":getDpRendering, "Description":getDpDescription}

modbusOutFile = open(directoryPrefix + corePrefix + "modbusOut.csv", "w+")
metaOutFile = open(directoryPrefix + corePrefix + "metaOut.csv", "w+")
writeHeader(modbusHeaders, modbusOutFile)
writeHeader(metaHeaders, metaOutFile)
for dp in config["dataPoints"] :
	if re.search("MODBUS", dsXidMap[dp["dataSourceXid"]]["type"]) is not None :
		writeItem(modbusHeaders, modbusPropertyMap, dp, modbusOutFile)
	elif re.search("META", dsXidMap[dp["dataSourceXid"]]["type"]) is not None :
		writeItem(metaHeaders, metaPropertyMap, dp, metaOutFile)
modbusOutFile.close()
metaOutFile.close()

if delimiterDetected :
	print "A false delimiter exists in the data, consider changing your delimiter for this data!"