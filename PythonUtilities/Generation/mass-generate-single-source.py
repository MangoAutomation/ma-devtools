
offset = 14
dataSources=10
pointsPerSource=5000

DS_XID = "tuna-melt{}-{}-points".format(offset,pointsPerSource)

output = open("{}-config.json".format(DS_XID,pointsPerSource), "w+")

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
         "readPermission":"user-read",
         "setPermission":"user-set",
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
output.write(dataSource_base % {"DS_XID":DS_XID})
output.write("],\"dataPoints\":[")

for x in xrange(pointsPerSource) :
	line = brownian_base % {"DS_XID":DS_XID, "count":offset+x}
	output.write(line)
	if x < pointsPerSource-1:
		output.write(',')
	output.write("\n")
output.write("]\n}")
