package viettelrd.rrd.services;


import static org.rrd4j.ConsolFun.AVERAGE;
import static org.rrd4j.ConsolFun.MAX;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.rrd4j.ConsolFun;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.Util;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;

public class RRDService {
	String rrdFile = null;
	public final static String RRD_FILE_NAME = "rrd.rrd";
	
	public String getRrdFile() {
		return rrdFile;
	}
	
	
	public void setRrdFile(String rrdFile) {
		this.rrdFile = rrdFile;
	}
	
	public List<String> getDataSources() throws IOException {
		List<String> datasources = new ArrayList<String>();
		RrdDb rrdb = new RrdDb(RRD_FILE_NAME, rrdFile);
		String[] ds = rrdb.getDsNames();
		for (String string : ds) {
			datasources.add(string);
		 }
		return datasources;
	}
	
	public String loadGraph(String dsName) throws IOException {
		if(dsName == null || dsName.length()==0) return null;
		String graphPath = null;
		String basePath = System.getProperty("catalina.base");
		if(basePath != null) {
		  graphPath = basePath+"/webapps/ROOT/img/"+dsName+".png";
		} else {
			graphPath = dsName+".png";
		}			
		RrdDb rrdb = new RrdDb(RRD_FILE_NAME, rrdFile);		
		RrdGraphDef graphDef = new RrdGraphDef();
		long start = Util.getTimestamp(2012, 10, 18, 0, 0);
		long end = Util.getTimestamp(2012, 10, 20, 0, 0);
		
				
		graphDef.setStartTime(start);
		graphDef.setEndTime(end);
		graphDef.datasource(dsName, "viettel.rrd", dsName, ConsolFun.AVERAGE);
		graphDef.setFilename(graphPath);
		graphDef.setImageFormat("png");
		graphDef.setVerticalLabel("Server Resource");
		graphDef.line(dsName, Color.GREEN, dsName, 3);
		graphDef.setWidth(600);
		graphDef.setHeight(400);
		  
		graphDef.gprint(dsName, MAX, "max "+dsName+" = %.3f%s");	
		graphDef.gprint(dsName, AVERAGE, "avg "+dsName+" = %.3f%S\\r");
		  
		graphDef.print(dsName, AVERAGE, "avg "+dsName+" = %.3f%S\\r");
		graphDef.print(dsName, MAX, "max "+dsName+" = %.3f%s");
		  
		RrdGraph graph = new RrdGraph(graphDef);
		System.out.println(graph.getRrdGraphInfo().dump());
		
		return graphPath;
	}
	
	public static void main(String[] args) {
		RRDService rrdService = new RRDService();
		rrdService.setRrdFile("xml://home/tanhq/Desktop/WEEMO/rrd.xml");
		try {
			rrdService.loadGraph("cpu0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
