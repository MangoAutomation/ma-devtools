//Because this is kinda hacky, it will do the export when the script is validated.
// Therefore, we wrap it in an if(false) to avoid it affecting other script environments
// this would need to be corrected during validation/running (but you don't need to save)
// to set this to if(true). If this wasn't the global scripts we wouldn't have to worry about that.
if(false) {
//FILE TO OUTPUT TO
var file = new java.io.File("/tmp/csvtest.csv");
var endTime = new Date(2017,8,1,22,1,0).getTime(); //END OF PERIOD IN EPOCH MILLIS
var startTime = new Date(2017,8,1,22,0,0).getTime();//endTime - 1000*3600; //START OF PERIOD IN EPOCH MILLIS

var scriptPermissions = new com.serotonin.m2m2.rt.script.ScriptPermissions();
scriptPermissions.setDataSourcePermissions("superadmin");
scriptPermissions.setDataPointReadPermissions("superadmin");
scriptPermissions.setDataPointSetPermissions("superadmin");

var DataPointQuery = new com.serotonin.m2m2.rt.script.DataPointQuery(scriptPermissions, null, null);

//SET UP WHAT POINTS TO EXPORT DATA FROM HERE
var dataPoints = DataPointQuery.query('like(xid,Device*)&eq(name,Air Outlet Temperature)');

//YOU CAN USE MORE THAN ONE QUERY....
/dataPoints.addAll(DataPointQuery.query('like(xid,Device*)&eq(name,Air Outlet Temperature)'))

var fWriter = new java.io.FileWriter(file, false);
var pWriter = new java.io.PrintWriter(fWriter);
var pvd = com.serotonin.m2m2.Common.databaseProxy.newPointValueDao();

var exportCsvStreamer = new com.serotonin.m2m2.vo.export.ExportCsvStreamer("", 80, pWriter, com.serotonin.m2m2.Common.getTranslations());
var exportDataValue = new com.serotonin.m2m2.vo.export.ExportDataValue();
var watchlistCallback = function(pvt, rowIndex) {
    exportDataValue.setValue(pvt.value);
    exportDataValue.setTime(pvt.time);
    if(typeof pvt.annotation === 'string')
        exportDataValue.setAnnotation(pvt.annotation);
    else
        exportDataValue.setAnnotation(null);
        
    exportCsvStreamer.pointData(exportDataValue);
};

var dpIdMap = new java.util.HashMap();
var dpIds = [];
for(var k = 0; k < dataPoints.length; k+=1) {
    var dpvo = com.serotonin.m2m2.db.dao.DataPointDao.instance.getDataPoint(dataPoints[k].xid);
    dpIdMap.put(dpvo.getId(), dpvo);
    dpIds.push(dpvo.getId());
}

for(var k = 0; k < dpIds.length; k+=1) {
    var dpvo = dpIdMap[dpIds[k]];
    var epi = new com.serotonin.m2m2.vo.export.ExportPointInfo();
    epi.setXid(dpvo.getXid());
    epi.setPointName(dpvo.getName());
    epi.setDeviceName(dpvo.getDeviceName());
    epi.setTextRenderer(dpvo.getTextRenderer());
    epi.setDataPointId(dpIds[k]);
    exportCsvStreamer.startPoint(epi);
    
    pvd.getPointValuesBetween(dpvo.getId(), startTime, endTime, watchlistCallback);
}
exportCsvStreamer.done();

throw "Success!";
}