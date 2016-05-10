import json

configFile = open("C:\\IA\\Inbound\\ThrowAway\\23\\envDs.txt")
config = json.load(configFile)
configFile.close()

baseStatement = "SELECT COUNT(*) FROM dataPoints WHERE xid IN ("
for dp in config["dataPoints"] :
	if "xid" in dp :
		baseStatement += "'" + dp["xid"] + "', "

baseStatement = baseStatement[:len(baseStatement)-2] + ");"
output = open("C:\\IA\\Inbound\\ThrowAway\\23\\out.sql", "w+")
output.write(baseStatement)
output.close()