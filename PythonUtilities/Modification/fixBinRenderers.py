import json
from StringIO import StringIO
import re
import copy

#This script uses the values from a CSV to set the binary renderers of an existing configuration.

baseBinaryRenderer = """{
            "type":"BINARY",
            "oneColour":null,
            "oneLabel":"NORMAL",
            "zeroColour":null,
            "zeroLabel":"ABNORMAL"
         }"""
baseBinr = json.load(StringIO(baseBinaryRenderer))

configFile = open("C:\\IA\\Inbound\\Throwaway\\12\\source.json")
config = json.load(configFile)
configFile.close()

def fixRenderer(dp, rend) :
	print("fixing" + rend)
	newRend = copy.deepcopy(baseBinr)
	oneVal = rend.split("=")[1].strip()
	if oneVal == "NORMAL" :
		zeroVal = "ABNORMAL"
	elif oneVal == "ALARM" :
		zeroVal = "NORMAL"
	elif oneVal == "TRIPPED" :
		zeroVal = "NOT TRIPPED"
	elif oneVal == "BKR CLOSED" or oneVal == " BKR CLOSED" :
		zeroVal = "BKR OPEN"
	elif oneVal == "DISABLED" :
		zeroVal = "ENABLED"
	elif oneVal == "ASSERTED" :
		zeroVal = "NOT ASSERTED"
	elif oneVal == "REMOTE ENABLED" :
		zeroVal = "REMOTE DISABLED"
	elif oneVal == "RACKED IN" :
		zeroVal = "NOT RACKED IN"
	elif oneVal == "ENABLED" :
		zeroVal = "DISABLED"
	else :
		print "UNKNOWN ONEVAL: " + oneVal
	newRend["oneLabel"] = oneVal
	newRend["zeroLabel"] = zeroVal
	dp["textRenderer"] = newRend

		 
definition = open("C:\\IA\\Inbound\\AES\\IPL\\input.csv")
for line in definition :
	ld = line.split(",")
	if len(ld) < 10 or re.search("1=.*", ld[9]) is None :
		print("continuing...  :", ld)
		continue
	for dp in config["dataPoints"] :
		if dp["pointLocator"]["range"] == "INPUT_STATUS" and int(ld[0]) == dp["pointLocator"]["offset"] :
			fixRenderer(dp, ld[9])
		 
outputFile = open("C:\\IA\\Inbound\\Throwaway\\12\\output.txt", "w+")
outputFile.write(json.dumps(config, sort_keys=False, indent=4, separators=(',',': ')))
outputFile.close()