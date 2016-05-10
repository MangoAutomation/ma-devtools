output = open("C:\\IA\\SnakePit\\Generic Generators\\small-repeat-out.txt", "w+")

base = """{
         "name":"Alarm #%(num)d",
         "enabled":true,
         "loggingType":"ALL",
         "intervalLoggingPeriodType":"MINUTES",
         "intervalLoggingType":"INSTANT",
         "purgeType":"YEARS",
         "pointLocator":{
            "range":"INPUT_REGISTER",
            "modbusDataType":"FOUR_BYTE_INT_UNSIGNED",
            "writeType":"NOT_SETTABLE",
            "additive":0.0,
            "bit":0,
            "charset":"ASCII",
            "multiplier":0.1,
            "offset":%(offsetNum)d,
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
         "dataSourceXid":"DS_960366",
         "defaultCacheSize":1,
         "deviceName":"Core 01 Environment",
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
            "useUnitAsSuffix":false,
            "unit":"",
            "renderedUnit":"",
            "suffix":""
         },
         "tolerance":0.0
      },
	  """
			
for x in xrange(50) :
	output.write(base % {"num":x+1, "offsetNum":30996-(x*2)})