package viettelrd.rrd.test;

import static org.rrd4j.ConsolFun.AVERAGE;
import static org.rrd4j.ConsolFun.MAX;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.rrd4j.ConsolFun;
import org.rrd4j.core.Datasource;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.Util;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;

public class ImportDB {
  public static void main(String[] args) {
	  try {
		  RrdDb rrdb = new RrdDb("rrd.rrd", "xml://home/tanhq/Desktop/WEEMO/rrd.xml");		 
		  RrdGraphDef graphDef = new RrdGraphDef();
		  SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
		  df.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		  long start = Util.getTimestamp(2012, 10, 18, 0, 0);
		  long end = Util.getTimestamp(2012, 10, 20, 0, 0);
		  
				  
		  graphDef.setStartTime(start);
		  graphDef.setEndTime(end);
		  graphDef.datasource("CPU7", "viettel.rrd", "cpu0", ConsolFun.AVERAGE);
		  graphDef.setFilename("cpu7.png");
		  graphDef.setImageFormat("png");
		  graphDef.setVerticalLabel("Server Resource");
		  graphDef.line("CPU7", Color.GREEN, "CPU7");
		  
		  graphDef.gprint("CPU7", MAX, "max CPU7 = %.3f%s");	
		  graphDef.gprint("CPU7", AVERAGE, "avg CPU7 = %.3f%S\\r");
		  
		  graphDef.print("CPU7", AVERAGE, "avg CPU7 = %.3f%S\\r");
		  graphDef.print("CPU7", MAX, "max CPU7 = %.3f%s");

		  
		  
		  RrdGraph graph = new RrdGraph(graphDef);
		  System.out.println(graph.getRrdGraphInfo().dump());
		  //BufferedImage bi = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
		  //graph.render(bi.getGraphics());
		  
	  } catch(Exception e) {
		  e.printStackTrace();
	  }
  }
}
