import json

dirPrefix = "C:\\IA\\Inbound\\Throwaway\\20\\"
outPrefix = "tait-"

threadFile = open(dirPrefix + "threads.json")
threads = json.load(threadFile)
threadFile.close()

#I want to get a composed set of current positions, a section of blocked threads converted to readable stack traces
# and I want stack trace sections ordered by time.

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
	if "location" in thread and len(thread["location"]) > 0 :
		currentLocation = thread["location"][0];
		currentPlace = currentLocation["methodName"] + " : " + currentLocation["className"] + ":" + str(currentLocation["lineNumber"])
		if currentPlace in currentPlaces :
			currentPlaces[currentPlace] += 1
		else :
			currentPlaces[currentPlace] = 1

def writeThreadList(threadSet, file) :
	file.write("Count: " + str(len(threadSet)) + 
"\n________________________________________________________________________________\n")
	for t in threadSet :
		if len(t["location"]) > 0 :
			file.write("\n    @ %(fileName)s:%(methodName)s:%(lineNumber)d" % 
				{ "fileName":t["location"][0]["fileName"],
				"methodName":t["location"][0]["methodName"],
				"lineNumber":t["location"][0]["lineNumber"]})
		file.write("""
		CpuTime: %(cpuTime)d\tUserTime: %(userTime)s\tThread: %(name)s\tID: %(id)d
		--=====--=====--=====--=====--=====--=====--=====--=====--""" % {"id":t["id"], "cpuTime": t["cpuTime"], "name": t["name"], "userTime":t["userTime"]})
		for loc in t["location"] :
			file.write("""
	  %(className)s:%(methodName)s:%(lineNumber)d""" % {"className":loc["className"], "methodName": loc["methodName"], "lineNumber": loc["lineNumber"]})
		file.write("""
________________________________________________________________________________
	""")
			
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

for key, value in currentPlaces.iteritems() :
	currentPlacesFile.write(key + "\t" + str(value) + "\n")
	
currentPlacesFile.write("\n\n==============UNCLASSIFIED THREADS==================\n")
writeThreadList(otherThreads, currentPlacesFile)
	
currentPlacesFile.close()
