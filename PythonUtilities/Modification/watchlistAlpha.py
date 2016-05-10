import json

configf = open("C:\\IA\\Inbound\\Throwaway\\9\\config.txt", "r")
config = json.load(configf)
configf.close()

xidMap = {}
for dp in config["dataPoints"] :
	xidMap[dp["xid"]] = dp
	
for wl in config["watchLists"] :
	wl["dataPoints"].sort(key=lambda x: xidMap[x]["name"])
	
result = open("C:\\IA\\Inbound\\Throwaway\\9\\result.txt", "w+")
result.write(json.dumps(config, sort_keys=False, indent=4, separators=(',',': ')))
result.close()