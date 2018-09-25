import json
from math import log10
import os
import re

dirPrefix = "C:\\IA\\Days\\9-25-18\\"
outPrefix = "c-"
filename = "c.json"
#file, function, dir
type="dir"
dirRegex=".*[.]json$"

#Formatting...
def writeThreadList(threadSet, file) :
	file.write("Count: " + str(len(threadSet)) + 
"\n________________________________________________________________________________\n")
	for t in threadSet :
		if "location" in t and t["location"] is not None and len(t["location"]) > 0 :
			file.write("\n    @ %(fileName)s:%(methodName)s:%(lineNumber)d" % 
				{ "fileName":t["location"][0]["fileName"],
				"methodName":t["location"][0]["methodName"],
				"lineNumber":t["location"][0]["lineNumber"]})
		else:
			file.write("\n    @Unknown location")
		try :
			l10 = log10(t["cpuTime"])
		except :
			l10 = 15
		file.write("""
		CpuTime: %(cpuTime)d\tUserTime: %(userTime)s\tLogTime: %(logTime)d\tThread: %(name)s\tID: %(id)d
		--=====--=====--=====--=====--=====--=====--=====--=====--""" % {"id":t["id"], "cpuTime": t["cpuTime"], "logTime": l10, "name": t["name"], "userTime":t["userTime"]})
		if "location" in t and t["location"] is not None :
			for loc in t["location"] :
				file.write("""
	  %(className)s:%(methodName)s:%(lineNumber)d""" % {"className":loc["className"], "methodName": loc["methodName"], "lineNumber": loc["lineNumber"]})
		file.write("""
________________________________________________________________________________
	""")


#I want to get a composed set of current positions, a section of blocked threads converted to readable stack traces
# and I want stack trace sections ordered by time.
def parseThreads(dirPrefix, filename, outPrefix) :
	threadFile = open(dirPrefix + filename)
	threads = json.load(threadFile)
	threadFile.close()

	currentPlaces = {}
	blockedThreads = []
	parkedThreads = []
	runnableThreads = []
	otherThreads = []
	for thread in threads :
		if "state" in thread:
			if thread["state"] == "BLOCKED" :
				blockedThreads.append(thread)
			elif thread["state"] == "TIMED_WAITING" or thread["state"] == "WAITING" :
				parkedThreads.append(thread)
			elif thread["state"] == "RUNNABLE" :
				runnableThreads.append(thread)
			else :
				otherThreads.append(thread)
		if "location" in thread and thread["location"] is not None and len(thread["location"]) > 0 :
			currentLocation = thread["location"][0];
			currentPlace = currentLocation["methodName"] + " : " + currentLocation["className"] + ":" + str(currentLocation["lineNumber"])
			if currentPlace in currentPlaces :
				currentPlaces[currentPlace] += 1
			else :
				currentPlaces[currentPlace] = 1
		elif "none" not in currentPlaces :
			currentPlaces["none"] = 1
		else :
			currentPlaces["none"] += 1
				
	blockedThreads.sort(key=lambda x: 0 if "cpuTime" not in x else int(x["cpuTime"]), reverse=True)			
	blockedThreadsFile = open(dirPrefix + outPrefix + "blockedThreads.out", "w+")
	writeThreadList(blockedThreads, blockedThreadsFile)
	blockedThreadsFile.close()

	parkedThreads.sort(key=lambda x: 0 if "cpuTime" not in x else int(x["cpuTime"]), reverse=True)	
	parkedThreadsFile = open(dirPrefix + outPrefix + "parkedThreads.out", "w+")
	writeThreadList(parkedThreads, parkedThreadsFile)
	parkedThreadsFile.close()

	runnableThreads.sort(key=lambda x: 0 if "cpuTime" not in x else int(x["cpuTime"]), reverse=True)			
	runnableThreadsFile = open(dirPrefix + outPrefix + "runnableThreads.out", "w+")
	writeThreadList(runnableThreads, runnableThreadsFile)
	runnableThreadsFile.close()

	currentPlacesFile = open(dirPrefix + outPrefix + "currentPlaces.out", "w+")

	for key, value in currentPlaces.items() :
		currentPlacesFile.write(key + "\t" + str(value) + "\n")

	currentPlacesFile.write("\n\n==============THREAD TYPE COUNTS==================\n")		
	currentPlacesFile.write("\tBlocked: %d\n\tParked: %d\n\tRunnable: %d\n\tUnclassified: %d\n"%(len(blockedThreads), len(parkedThreads), len(runnableThreads), len(otherThreads)))
	
	currentPlacesFile.write("\n\n==============UNCLASSIFIED THREADS==================\n")
	writeThreadList(otherThreads, currentPlacesFile)
		
	currentPlacesFile.close()

if type == "file" :
	parseThreads(dirPrefix, filename, outPrefix)
elif type == "function" : #customize on your own
	for x in xrange(11) :
		parseTheads(dirPrefix, str(x+1)+".json", str(x+1))
elif type == "dir" :
	for f in os.listdir(dirPrefix) :
		if re.search(dirRegex, f) is not None :
			parseThreads(dirPrefix, f, f+"-")