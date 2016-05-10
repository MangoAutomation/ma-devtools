import re
import os

filename = "processed-02-"
pathPrefix = "C:\\IA\\Inbound\\ThrowAway\\1\\"

outputNodes = open(pathPrefix + filename + ".nodes", "w+")
outputOther = open(pathPrefix + filename + ".other", "w+")

nodes = {}
isLog = re.compile("ma\.log")
pattern = re.compile(':106\) - (.*):.*poll.*aborted')
disregard = re.compile('MangoNoSqlPointValueDao[.]deletePointValuesBefore')
for name in os.listdir(pathPrefix) :
	match = isLog.search(name)
	if not match :
		continue
	input = open(pathPrefix + name, "r")
	for line in input.readlines() :
		#print line
		match = pattern.search(line)
		if match and match.group(1) != "" :
			#print("Found match")
			if match.group(1) in nodes :
				nodes[match.group(1)] += 1
			else :
				nodes[match.group(1)] = 1
			continue
			
		match = disregard.search(line)
		if match :
			continue
		else :
			outputOther.write(line);
	input.close()
			
nodesList = nodes.keys()
nodesList.sort()
total = 0
for node in nodesList :
	total += nodes[node]
	outputNodes.write(node + ": " + str(nodes[node]) + "\n")

outputNodes.write("\nTotal aborted: \n" + str(total))

outputNodes.close()
outputOther.close()