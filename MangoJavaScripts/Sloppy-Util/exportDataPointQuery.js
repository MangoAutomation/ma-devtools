//FILE TO OUTPUT TO
var file = new java.io.File("/tmp/testCsvWriter.csv");
var endTime = new Date().getTime(); //END OF PERIOD IN EPOCH MILLIS
var startTime = 0;//endTime - 1000*3600; //START OF PERIOD IN EPOCH MILLIS
var csvWriterType = Java.type("au.com.bytecode.opencsv.CSVWriter");
var fWriter = new java.io.FileWriter(file, false);
var pWriter = new java.io.PrintWriter(fWriter);
var pvd = com.serotonin.m2m2.Common.databaseProxy.newPointValueDao();
//var csvWriter = new csvWriterType(fWriter);
//var csvPojoWriter = new com.serotonin.m2m2.web.mvc.rest.v1.csv.CSVPojoWriter(csvWriter);

//csvPojoWriter.close();

var exportCsvStreamer = new com.serotonin.m2m2.vo.export.ExportCsvStreamer("", 80, pWriter, com.serotonin.m2m2.Common.getTranslations());
/* Test watchlist download.....*/
var WatchListDwr = new com.serotonin.m2m2.watchlist.WatchListDwr();

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

var scriptPermissions = new com.serotonin.m2m2.rt.script.ScriptPermissions();
scriptPermissions.setDataSourcePermissions("superadmin");
scriptPermissions.setDataPointReadPermissions("superadmin");
scriptPermissions.setDataPointSetPermissions("superadmin");

var DataPointQuery = new com.serotonin.m2m2.rt.script.DataPointQuery(scriptPermissions, null, null);

//SET UP WHAT POINTS TO EXPORT DATA FROM HERE
var dataPoints = DataPointQuery.query('eq(xid,internal_mango_num_excel_reports)');

//YOU CAN USE MORE THAN ONE QUERY....
//dataPoints.addAll(DataPointQuery.query('like(xid,SDGE*)&eq(name,Outside Air Temperature)'))
var dpIdMap = new java.util.HashMap();
var intergetArrayList = Java.type("java.lang.Integer[]")
//var dpIds = new java.util.ArrayList();
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
//throw WatchListDwr.getImageChartData(Java.to(dpIds, "int[]"), 2017, 11, 3, 12, 0, 0, false, 2017, 11, 3, 20, 0, 0, true );

/*Disabled CSV stream to try watchlist
//String host, int port, Map<Integer,DataPointVO> pointMap, boolean useRendered,  boolean unitConversion, long from, long to, PointValueDao dao, Integer limit
var dataStreamer = new com.serotonin.m2m2.web.mvc.rest.v1.model.pointValue.XidPointValueTimeMapDatabaseStream("", 80, 
    dpIdMap, false, false, startTime, endTime, com.serotonin.m2m2.Common.databaseProxy.newPointValueDao(), 10);

dataStreamer.streamData(csvPojoWriter);
*/

throw "Success!";
