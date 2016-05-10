import json
import types
import re

directoryPrefix = "C:\\IA\\Inbound\\ThrowAway\\"
serverPrefix = "Server-"

removePh = True
addAborted = True
abortedFile = "logs\\processed.nodes"

configFile = open(directoryPrefix + "Mango-Configuration.json")

filterDSProps = ["alarmLevels", "contiguousBatches", "createSlaveMonitorPoints", "discardDataDelay", ("enabled",True), "encapsulated",
			"type", "transportType", "maxHistoricalIOLogs", "quantize", ("updatePeriodType", "SECONDS"), ("purgeOverride", False),
			("purgeType","YEARS"), ("retries", 2), ("timeout", 500), ("purgePeriod", 1), "host", ("maxWriteRegisterCount", 120), 
			("maxReadRegisterCount", 80), ("maxReadBitCount", 2000), ("logIO", False), ("multipleWritesOnly", False), ("ioLogFileSizeMBytes", 1.0),
			"xid", ("updatePeriods",2),"port"]
			
basicDSProps = ["name"]

filterDataPointProps = ["intervalLoggingPeriod","intervalLoggingPeriodType","loggingType","discardLowLimit","discardHighLimit","xid","dataSourceXid",
						"plotType", ("purgeOverride",False), "chartColour", ("discardExtremeValues",False),"chartRenderer",("intervalLoggingType","INSTANT"),
						("overrideIntervalLoggingSamples",False), "purgePeriod","unit","tolerance","textRenderer",("enabled",True),("defaultCacheSize",1),
						("intervalLoggingSampleWindowSize",0),("eventDetectors",[]),("purgeType","YEARS"),"pointLocator","purgeOverride"]
						
basicDataPointProps = ["name","deviceName"]
						
filterPointLocatorProps = [("additive",0.0),("multiplier",1.0),("charset","ASCII"),"slaveMonitor","writeType",("bit",0),
							("range","HOLDING_REGISTER")]
			
filterUserProps = ["dataPointPermissions", "dataSourcePermissions", "admin", "disabled", "homeUrl", "password", 
			"timezone", "receiveOwnAuditEvents", "phone", "muted"]
			
filterWatchListProps = ["dataPoints", "sharingUsers"]
			
abortedDict = {}
if addAborted :
	pattern = re.compile("(.*?):.*?([0-9]+)")
	nf = open(directoryPrefix + abortedFile)
	for line in nf.readlines() :
		match = pattern.search(line)
		if match :
			abortedDict[match.group(1)] = int(match.group(2))
	nf.close()
			
def clearOtherProps(item, props) :
	for prop in props :
		if type(prop) == types.StringType :
			if prop in item : 
				item.pop(prop)
		elif type(prop) == types.TupleType :
			if prop[0] in item and item[prop[0]] == prop[1] :
				item.pop(prop[0])
				
def isBasic(item, props) :
	itemKeys = item.keys()
	for prop in itemKeys:
		found = False
		for p in props :
			if p == prop :
				found = True
		if not found :
			return False
	return True
				

myObj = json.load(configFile)

if "dataSources" in myObj :
	dsList = myObj.pop("dataSources")
if "dataPoints" in myObj :
	dpList = myObj.pop("dataPoints")
if "watchLists" in myObj :
	watchLists = myObj.pop("watchLists")
	if "watchlists" in myObj :
		myObj.pop("watchlists")
if "users" in myObj :
	users = myObj.pop("users")
if removePh and "pointHierarchy" in myObj :
	myObj.pop("pointHierarchy")

resultList = []
for ds in dsList :
	clearOtherProps(ds, filterDSProps)
	if not isBasic(ds, basicDSProps) :
		resultList.append(ds)
	if addAborted and ds["name"] in abortedDict :
		ds["aborted"] = abortedDict[ds["name"]]
		
if addAborted :
	resultList.sort(key=lambda x: 0 if "aborted" not in x else x["aborted"], reverse=True)
dataSources = open(directoryPrefix + serverPrefix + "data-sources.json", "w+")
dataSources.write(json.dumps(resultList, sort_keys=True, indent=4, separators=(',',': ')))
dataSources.close()
	
resultList = []
for dp in dpList :
	clearOtherProps(dp, filterDataPointProps)
	if "pointLocator" in dp :
		clearOtherProps(dp["pointLocator"], filterPointLocatorProps)
	if not isBasic(dp, basicDataPointProps) :
		resultList.append(dp)
dataPoints = open(directoryPrefix + serverPrefix + "data-points.json", "w+")
dataPoints.write(json.dumps(resultList, sort_keys=True, indent=4, separators=(',',': ')))
dataPoints.close()
		
for u in users :
	clearOtherProps(u, filterUserProps)
for w in watchLists :
	clearOtherProps(w, filterWatchListProps)



usersFile = open(directoryPrefix + serverPrefix + "users.json", "w+")
watchListsFile = open(directoryPrefix + serverPrefix + "watchlists.json", "w+")
otherConfig = open(directoryPrefix + serverPrefix + "other-config.json", "w+")

usersFile.write(json.dumps(users, sort_keys=True, indent=4, separators=(',',': ')))
usersFile.close()

watchListsFile.write(json.dumps(watchLists, sort_keys=True, indent=4, separators=(',',': ')))
watchListsFile.close()

otherConfig.write(json.dumps(myObj, sort_keys=True, indent=4, separators=(',',': ')))
otherConfig.close()

