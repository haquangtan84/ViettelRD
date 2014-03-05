package org.rrd4j.demo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.rrd4j.ConsolFun;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;


class Test {
    
    public static void main(String[] args) throws IOException, ParseException {
    	  RrdGraphDef graphDef = new RrdGraphDef();
		  SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
		  df.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		  Date start = df.parse("Nov 18 2012 00:00:00");		  
		  Date end = df.parse("Nov 20 2012 00:00:00");
		  System.out.println(start.getTime());
		  System.out.println(end.getTime());
		  graphDef.setTimeSpan(1353171600, 1353344400);
		  graphDef.datasource("faf", "rrd.rrd", "cpu7", ConsolFun.AVERAGE);
		  graphDef.setFilename("speed.gif");
		  graphDef.setImageFormat("png");		  
		  
		  RrdGraph graph = new RrdGraph(graphDef);
		  System.out.println(graph.getRrdGraphInfo().getBytes());
    }
}
