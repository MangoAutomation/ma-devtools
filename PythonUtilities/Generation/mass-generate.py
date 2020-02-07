#Start offset for IDS
offset = 1
dataSources=2
pointsPerSource=2

output = open("{}-{}-sources-{}-pointsPer-config.json".format(offset,dataSources,pointsPerSource), "w+")

dataSource_base = """{
         "xid":"%(DS_XID)s",
         "name":"%(DS_XID)s",
         "enabled":false,
         "type":"VIRTUAL",
         "alarmLevels":{
            "POLL_ABORTED":"URGENT"
         },
         "purgeType":"YEARS",
         "updatePeriodType":"MILLISECONDS",
         "updatePeriods":500,
         "editPermission":"",
         "purgeOverride":false,
         "purgePeriod":1
      }"""

brownian_base = """{
         "xid":"%(DS_XID)s_Brownian_%(count)d",
         "name":"Brownian_%(count)d",
         "enabled":true,
         "loggingType":"INTERVAL",
         "intervalLoggingPeriodType":"MINUTES",
         "intervalLoggingType":"AVERAGE",
         "purgeType":"YEARS",
         "pointLocator":{
            "dataType":"NUMERIC",
            "changeType":{
               "type":"BROWNIAN",
               "max":100.0,
               "maxChange":10.0,
               "min":0.0,
               "startValue":50
            },
            "settable":true
         },
         "eventDetectors":[
            {
                "type":"POINT_CHANGE",
                "sourceType":"DATA_POINT",
                "name":"Value Changed",
                "alarmLevel":"INFORMATION"
            }
         ],
         "tags": {
             "site":"Denver",
             "measurement":"load-test",
             "state": "development-%(DS_XID)s",
             "color": "black"
         },
         "plotType":"SPLINE",
         "unit":"",
         "templateXid":"Numeric_Default",
         "chartColour":"black",
         "chartRenderer":{
            "type":"IMAGE",
            "timePeriodType":"DAYS",
            "numberOfPeriods":1
         },
         "dataSourceXid":"%(DS_XID)s",
         "defaultCacheSize":1,
         "deviceName":"%(DS_XID)s",
         "discardExtremeValues":false,
         "discardHighLimit":1.7976931348623157E308,
         "discardLowLimit":-1.7976931348623157E308,
         "intervalLoggingPeriod":1,
         "intervalLoggingSampleWindowSize":0,
         "overrideIntervalLoggingSamples":false,
         "purgeOverride":false,
         "purgePeriod":1,
         "readPermission":"%(DS_XID)s-read",
         "setPermission":"%(DS_XID)s-set",
         "textRenderer":{
            "type":"ANALOG",
            "useUnitAsSuffix":true,
            "unit":"",
            "renderedUnit":"",
            "format":"0.00"
         },
         "tolerance":0.0
      }"""

output.write("{\n");
output.write("\"dataSources\":[")
for ds in xrange(dataSources) :
    DS_XID = "tuna-melt{}-{}-points".format(offset+ds,pointsPerSource)
    output.write(dataSource_base % {"DS_XID":DS_XID})
    if ds < dataSources-1:
        output.write(',')
	output.write("\n")

output.write("],\"dataPoints\":[")

for ds in range(dataSources) :
	DS_XID = "tuna-melt{}-{}-points".format(offset+ds,pointsPerSource)
	for x in range(pointsPerSource) :
		line = brownian_base % {"DS_XID":DS_XID, "count":offset+x}
		output.write(line)
		if x < pointsPerSource - 1 :
			output.write(',')
	if ds < dataSources -1 :
		output.write(',')

output.write("],\n\t\"roles\":[\n")

for ds in range(dataSources) :
	PREFIX = "tuna-melt{}-{}-points".format(offset+ds,pointsPerSource)
	output.write("""\t\t{"xid": "%(rolePrefix)s-set", "name":"%(rolePrefix) set role"},\n""" % {"rolePrefix": PREFIX});
	output.write("""\t\t{"xid": "%(rolePrefix)s-read", "name":"%(rolePrefix) read role"}""" % {"rolePrefix": PREFIX});
	if ds < dataSources -1 :
		output.write(',\n')

output.write("]\n}")
