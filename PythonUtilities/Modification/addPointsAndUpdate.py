import json
import re
import copy

dir = "C:\\IA\\Inbound\\ThrowAway\\"
configFileName = "Mango-Configuration.json"
addingFileName = "adding.json"

configFile = open(dir+configFileName)
config = json.load(configFile)
configFile.close()

addingFile = open(dir+addingFileName)
addingJson = json.load(addingFile)
addingFile.close()

if "dataPoints" not in addingJson :
	print "MUST HAVE DATA POINT LIST IN ADDING POINTS JSON OBJECT"
	exit()

if "dataPoints" not in config :
	print "MUST HAVE DATA POINT LIST IN CONFIG JSON OBJECT"
	exit()
	
if "dataSources" not in config :
	print "MUST HAVE DATA SOURCES LIST IN CONFIG JSON OBJECT"
	exit()
	
addingPoints = addingJson["dataPoints"]
pointsList = config["dataPoints"]
sourcesList = config["dataSources"]
watchlists = config["watchlists"]
hierarchy = config["pointHierarchy"][0]

def createXidDict(list) :
	r = {}
	for item in list :
		r[item["xid"]] = item
	return r
	
def getNodeNum(source) :
	match=re.search("Node (\d+)", source["name"])
	return None if match is None else int(match.group(1))
	
def padNum(num) :
	return str(num) if int(num) > 9 else "0" + str(num)

#This should perform the renaming
def modifyPointForSource(point, source) :
	num = getNodeNum(source)
	paddedNum = padNum(num)
	if num is not None :
		point["xid"] = re.sub("c1n1","c1n%(num)d"%{"num":num},point["xid"])
		point["deviceName"] = re.sub("Node 01","Node %(paddedNum)s"%{"paddedNum":paddedNum},point["deviceName"])
	point["dataSourceXid"] = source["xid"]
	return point
	
#This should return a list of tuples, where each tuple has a watchlist object
# and a function to define if it gets a point.
def getWatchlistPairs(source) :
	ans = []
	paddedNum = padNum(getNodeNum(source))
	for watchlist in watchlists :
		if watchlistNameSearch(watchlist["name"], "Node " + paddedNum + " Faults BMS") :
			ans.append((watchlist, lambda x: re.search("Fault", x["name"]) is not None))
		elif watchlistNameSearch(watchlist["name"], "Node " + paddedNum + " BMS") :
			ans.append((watchlist, lambda x: True))
	return ans

def watchlistNameSearch(watchlistName, name) :
	return re.search(name, watchlistName) is not None
	
def toWatchlistForm(point) :
	return point["xid"]
	
#Get the folder, DFS greedy
def getHierarchyFolder(source) :
	name = source["name"]
	for folder in hierarchy["subfolders"] :
		ans = getHierarchyFolderRecur(folder, name)
		if ans is not None :
			return ans
	return None
	
def getHierarchyFolderRecur(folder, name) :
	for subfolder in folder["subfolders"] :
		ans = getHierarchyFolderRecur(subfolder, name)
		if ans is not None :
			return ans
	return folder if folderNamesearch(folder["name"], name) else None
	
def folderNamesearch(folderName, name) :
	nameGetter = re.search("Node (\d+(?: BMS)?)", name)
	if nameGetter is None :
		return False
	return re.search(nameGetter.group(1), folderName) is not None
	
def toPointHierarchyForm(point) :
	return point["xid"]
	
#Fix those things that sometimes somehow exist maybe
def fixAttrs(point) :
	if point["chartColour"] == None :
		point["chartColour"] = ""
	
for point in pointsList :
	fixAttrs(point)

#This should handle adding all the points 
def meetsAddCriteria(source) :
	return re.search("BMS", source["name"]) is not None
	
for source in sourcesList :
	if meetsAddCriteria(source) :
		watchlistPairList = getWatchlistPairs(source)
		hierarchyFolder = getHierarchyFolder(source)
		for point in addingPoints :
			addingPoint = modifyPointForSource(copy.deepcopy(point), source)
			pointsList.append(addingPoint)
			hierarchyFolder["points"].append(toPointHierarchyForm(addingPoint))
			for watchlistPair in watchlistPairList :
				if watchlistPair[1](addingPoint) :
					watchlistPair[0]["dataPoints"].append(toWatchlistForm(addingPoint))
			
#I still have to alphabatize the watchlists...
pointsDict = createXidDict(pointsList)
for watchlist in watchlists :
	watchlist["dataPoints"].sort(key=lambda x: pointsDict[x]["name"])

outputFile = open(dir+"addResult.json", "w+")
outputFile.write(json.dumps(config, sort_keys=False, indent=4, separators=(',',': ')))
outputFile.close()

#Or we can split out individual sections for import / testing...

#pointHierarchyOut = open(dir+"phResult.json", "w+")
#pointHierarchyOut.write(json.dumps(config["pointHierarchy"], sort_keys=False, indent=4, separators=(',',': ')))
#pointHierarchyOut.close()

#watchlistsOut = open(dir+"watchlist.json", "w+")
#watchlistsOut.write(json.dumps(config["watchlists"], sort_keys=False, indent=4, separators=(',',': ')))
#watchlistsOut.close()
			
			
			
			
			
			
			
			
			
			
